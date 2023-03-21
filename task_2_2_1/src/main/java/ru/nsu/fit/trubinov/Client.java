package ru.nsu.fit.trubinov;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Client implements Runnable {
    private final int id;
    private final int minTimeBetweenOrders;
    private final Orders orders;
    private final int maxTimeBetweenOrders;
    private Signal signal;

    public Client(int id, Signal signal, Orders orders, int minTimeBetweenOrders, int maxTimeBetweenOrders) {
        this.id = id;
        this.signal = signal;
        this.orders = orders;
        this.minTimeBetweenOrders = minTimeBetweenOrders;
        this.maxTimeBetweenOrders = maxTimeBetweenOrders;
    }

    public void changeWorkingType(Signal signal) {
        this.signal = signal;
    }

    public void order(Pizza t) {
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(1000L * ThreadLocalRandom.current().nextInt(minTimeBetweenOrders, maxTimeBetweenOrders + 1));
            }
            if (signal != Signal.Work) {
                return;
            }
            synchronized (orders) {
                orders.add(t);
                log.info("Client №" + id + " ordered a pizza, there are " + orders.size() + " orders");
                orders.notifyAll();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (signal == Signal.Work) {
            order(new Pizza());
        }
        log.info("Client №" + id + " stopped doing orders");
    }
}
