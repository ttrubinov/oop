package ru.nsu.fit.trubinov;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) throws InterruptedException {
        BasicConfigurator.configure();
        Orders orders = new Orders();
        Storage storage = new Storage(5);
        Signal signal = Signal.Work;
        Baker baker1 = new Baker(1, signal, 5, orders, storage);
        Baker baker2 = new Baker(2, signal, 6, orders, storage);
        Courier courier1 = new Courier(1, signal, 4, storage);
        Courier courier2 = new Courier(2, signal, 7, storage);
        Client client1 = new Client(1, signal, orders, 2, 3);
        Client client2 = new Client(2, signal, orders, 4, 5);
        Thread[] threads = new Thread[6];
        threads[0] = new Thread(baker1);
        threads[1] = new Thread(courier1);
        threads[2] = new Thread(client1);
        threads[3] = new Thread(baker2);
        threads[4] = new Thread(courier2);
        threads[5] = new Thread(client2);
        for (int i = 0; i < 6; i++) {
            threads[i].start();
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
            baker1.changeWorkingType(signal);
            courier1.changeWorkingType(signal);
            client1.changeWorkingType(signal);
            baker2.changeWorkingType(signal);
            courier2.changeWorkingType(signal);
            client2.changeWorkingType(signal);
            if (signal == Signal.EmergencyInterrupt) {
                synchronized (orders) {
                    orders.notifyAll();
                }
                synchronized (storage) {
                    storage.notifyAll();
                }
                for (int i = 0; i < 6; i++) {
                    synchronized (threads[i]) {
                        threads[i].notifyAll();
                    }
                }
            }
        }
        log.info("Closing pizzeria");
        for (int i = 0; i < 6; i++) {
            threads[i].join();
        }
        log.info("Done closing pizzeria");
    }
}
