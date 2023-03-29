package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.state.State;
import ru.nsu.fit.trubinov.workers.Baker;
import ru.nsu.fit.trubinov.workers.Client;
import ru.nsu.fit.trubinov.workers.Courier;
import ru.nsu.fit.trubinov.workers.Stateful;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {
    private static final List<Stateful> workers = new ArrayList<>();
    private static Orders orders;
    private static Storage storage;
    private static State state;

    public static void main(String[] args) throws IOException, InterruptedException {
        BasicConfigurator.configure();
        initialize();
        startPizzeria();
    }

    private static void initialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        orders = new Orders();
        storage = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\storage.json"), new TypeReference<>() {
        });
        state = State.Work;

        List<Courier> couriers = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\couriers.json"), new TypeReference<>() {
        });
        for (Courier courier : couriers) {
            courier.changeState(state);
            courier.setStorage(storage);
        }
        log.info("Initialized couriers:\n" + couriers);

        List<Baker> bakers = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\bakers.json"), new TypeReference<>() {
        });
        for (Baker baker : bakers) {
            baker.changeState(state);
            baker.setStorage(storage);
            baker.setOrders(orders);
        }
        log.info("Initialized bakers:\n" + bakers);

        List<Client> clients = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\clients.json"), new TypeReference<>() {
        });
        for (Client client : clients) {
            client.changeState(state);
            client.setOrders(orders);
        }
        log.info("Initialized clients:\n" + clients);
        workers.addAll(clients);
        workers.addAll(couriers);
        workers.addAll(bakers);
    }

    private static void startPizzeria() throws InterruptedException {
        Thread[] threads = new Thread[workers.size()];
        for (int i = 0; i < workers.size(); i++) {
            threads[i] = new Thread(workers.get(i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        log.info("Pizzeria started working!");
        while (state == State.Work) {
            Scanner sc = new Scanner(System.in);
            String sig = sc.nextLine();
            try {
                state = State.valueOf(sig);
            } catch (Exception e) {
                log.error("Wrong signal");
            }
            if (state == State.Finish) {
                log.info("Closing pizzeria");
            } else if (state == State.EmergencyInterrupt) {
                log.info("Urgently closing pizzeria");
            }
            for (Stateful worker : workers) {
                worker.changeState(state);
            }
            if (state == State.EmergencyInterrupt) {
                synchronized (orders) {
                    orders.notifyAll();
                }
                synchronized (storage) {
                    storage.notifyAll();
                }
                for (int i = 0; i < threads.length; i++) {
                    synchronized (threads[i]) {
                        threads[i].interrupt();
                    }
                }
            }
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            log.info("Thread №" + i + " joined");
        }
        log.info("Done closing pizzeria");
    }
}
