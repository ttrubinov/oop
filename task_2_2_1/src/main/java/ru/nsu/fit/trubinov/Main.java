package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import ru.nsu.fit.trubinov.baker.Baker;
import ru.nsu.fit.trubinov.client.Client;
import ru.nsu.fit.trubinov.courier.Courier;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.signal.Signal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {
    private static Orders orders;
    private static Storage storage;
    private static Signal signal;
    private static List<Courier> couriers;
    private static List<Baker> bakers;
    private static List<Client> clients;

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
        signal = Signal.Work;

        couriers = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\couriers.json"), new TypeReference<>() {
        });
        for (Courier courier : couriers) {
            courier.setSignal(signal);
            courier.setStorage(storage);
        }
        log.info("Initialized couriers:\n" + couriers);

        bakers = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\bakers.json"), new TypeReference<>() {
        });
        for (Baker baker : bakers) {
            baker.setSignal(signal);
            baker.setStorage(storage);
            baker.setOrders(orders);
        }
        log.info("Initialized bakers:\n" + bakers);

        clients = mapper.readValue(new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_2_2_1\\src\\main\\resources\\clients.json"), new TypeReference<>() {
        });
        for (Client client : clients) {
            client.setSignal(signal);
            client.setOrders(orders);
        }
        log.info("Initialized clients:\n" + clients);
    }

    private static void startPizzeria() throws InterruptedException {
        int n = couriers.size() + bakers.size() + clients.size();
        Thread[] threads = new Thread[n];
        int threadIdx = 0;
        for (Baker baker : bakers) {
            threads[threadIdx] = new Thread(baker);
            threadIdx++;
        }
        for (Client client : clients) {
            threads[threadIdx] = new Thread(client);
            threadIdx++;
        }
        for (Courier courier : couriers) {
            threads[threadIdx] = new Thread(courier);
            threadIdx++;
        }
        for (Thread thread : threads) {
            thread.start();
        }
        log.info("Pizzeria started working!");
        while (signal == Signal.Work) {
            Scanner sc = new Scanner(System.in);
            String sig = sc.nextLine();
            try {
                signal = Signal.valueOf(sig);
            } catch (Exception e) {
                log.error("Wrong signal");
            }
            for (Baker baker : bakers) {
                baker.setSignal(signal);
            }
            for (Courier courier : couriers) {
                courier.changeWorkingType(signal);
            }
            for (Client client : clients) {
                client.changeWorkingType(signal);
            }
            if (signal == Signal.EmergencyInterrupt) {
                synchronized (orders) {
                    orders.notifyAll();
                }
                synchronized (storage) {
                    storage.notifyAll();
                }
                for (int i = 0; i < n; i++) {
                    synchronized (threads[i]) {
                        threads[i].notifyAll();
                    }
                }
            }
        }
        log.info("Closing pizzeria");
        for (int i = 0; i < n; i++) {
            threads[i].join();
            log.info("Thread №" + i + " joined");
        }
        log.info("Done closing pizzeria");
    }
}
