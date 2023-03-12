package ru.nsu.fit.trubinov;

import java.util.concurrent.ThreadLocalRandom;

public class Client implements Runnable {
    private final Orders orders;
    private int minTimeBetweenOrders;
    private int maxTimeBetweenOrders;

    public Client(Orders orders) {
        this.orders = orders;
    }

    public void order(Pizza t) {
        synchronized (orders) {
            orders.add(t);
            orders.notifyAll();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                wait(ThreadLocalRandom.current().nextInt(minTimeBetweenOrders, maxTimeBetweenOrders + 1));
                order(new Pizza());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
