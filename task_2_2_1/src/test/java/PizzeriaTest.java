import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.trubinov.pizza.Pizza;
import ru.nsu.fit.trubinov.queues.BlockingQueue;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.state.State;
import ru.nsu.fit.trubinov.workers.Baker;
import ru.nsu.fit.trubinov.workers.Client;
import ru.nsu.fit.trubinov.workers.Courier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PizzeriaTest {
    private static Orders orders;
    private static Storage storage;
    private static State state;

    public static Stream<BlockingQueue> queues() {
        return Stream.of(new Orders(), new Storage(5));
    }

    @BeforeEach
    void initialize() {
        BasicConfigurator.configure();
        orders = new Orders();
        storage = new Storage(5);
        state = State.Work;
    }

    @ParameterizedTest
    @MethodSource("queues")
    void QueuesTest(BlockingQueue queue) throws InterruptedException {
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        queue.add(new Pizza());
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        queue.take();
        assertEquals(0, queue.size());
    }

    @Test
    void BakerTest() throws InterruptedException {
        Baker baker1 = new Baker(1, 5);
        Baker baker2 = new Baker(2, 5);
        List<Baker> bakers = new ArrayList<>();
        bakers.add(baker1);
        bakers.add(baker2);
        for (Baker baker : bakers) {
            baker.setOrders(orders);
            baker.setStorage(storage);
            baker.changeState(state);
        }
        Thread[] threads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(bakers.get(i));
        }
        for (Thread thread : threads) {
            orders.add(new Pizza());
            thread.start();
        }
        synchronized (this) {
            wait(6 * 1000);
        }
        assertEquals(2, storage.size());
        orders.add(new Pizza());
        orders.add(new Pizza());
        for (Baker baker : bakers) {
            baker.changeState(State.Finish);
        }
        synchronized (this) {
            wait(6 * 1000);
        }
        assertEquals(4, storage.size());
        for (int i = 0; i < 2; i++) {
            threads[i].interrupt();
        }
        for (Baker baker : bakers) {
            baker.changeState(State.Work);
        }
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(bakers.get(i));
        }
        for (Thread thread : threads) {
            orders.add(new Pizza());
            thread.start();
        }
        for (Baker baker : bakers) {
            baker.changeState(State.EmergencyInterrupt);
        }
        synchronized (this) {
            wait(6 * 1000);
        }
        assertEquals(4, storage.size());
    }

    @Test
    void CourierTest() throws InterruptedException {
        Courier courier1 = new Courier(1, 5);
        Courier courier2 = new Courier(2, 5);
        List<Courier> couriers = new ArrayList<>();
        couriers.add(courier1);
        couriers.add(courier2);
        for (Courier courier : couriers) {
            courier.setStorage(storage);
            courier.changeState(state);
        }
        Thread[] threads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(couriers.get(i));
        }
        for (Thread thread : threads) {
            storage.add(new Pizza());
            storage.add(new Pizza());
            thread.start();
        }
        synchronized (this) {
            wait(6 * 2 * 1000);
        }
        assertEquals(0, storage.size());
        for (int i = 0; i < 4; i++) {
            storage.add(new Pizza());
        }
        for (Courier courier : couriers) {
            courier.changeState(State.Finish);
        }
        synchronized (this) {
            wait(6 * 4 * 1000);
        }
        assertEquals(0, storage.size());
        for (int i = 0; i < 2; i++) {
            threads[i].interrupt();
        }
        for (Courier courier : couriers) {
            courier.changeState(State.Work);
        }
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(couriers.get(i));
        }
        for (Thread thread : threads) {
            storage.add(new Pizza());
            storage.add(new Pizza());
            thread.start();
        }
        synchronized (this) {
            wait(1000);
        }
        for (Courier courier : couriers) {
            courier.changeState(State.EmergencyInterrupt);
        }
        synchronized (this) {
            wait(6 * 1000);
        }
        assertEquals(2, storage.size());
    }

    @Test
    void ClientTest() throws InterruptedException {
        Client client1 = new Client(1, 5, 6);
        Client client2 = new Client(2, 5, 6);
        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        for (Client client : clients) {
            client.setOrders(orders);
            client.changeState(state);
        }
        Thread[] threads = new Thread[2];
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(clients.get(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        synchronized (this) {
            wait(7 * 1000);
        }
        assertEquals(2, orders.size());
        for (Client client : clients) {
            client.changeState(State.Finish);
        }
        synchronized (this) {
            wait(7 * 2 * 1000);
        }
        assertEquals(2, orders.size());
        for (int i = 0; i < 2; i++) {
            threads[i].interrupt();
        }
        for (Client client : clients) {
            client.changeState(State.Work);
        }
        for (int i = 0; i < 2; i++) {
            threads[i] = new Thread(clients.get(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        synchronized (this) {
            wait(7 * 1000);
        }
        for (Client client : clients) {
            client.changeState(State.EmergencyInterrupt);
        }
        synchronized (this) {
            wait(7 * 2 * 1000);
        }
        assertEquals(4, orders.size());
    }
}
