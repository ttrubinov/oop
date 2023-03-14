package ru.nsu.fit.trubinov;

public class Baker implements Runnable {
    private int cookingTime;
    private final Orders orders;
    private final Storage storage;

    public Baker(Orders orders, Storage storage) {
        this.orders = orders;
        this.storage = storage;
    }

    public Pizza get() {
        try {
            wait(cookingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Pizza();
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (orders) {
                    while (orders.isEmpty()) {
                        orders.wait();
                    }
                    orders.take();
                    orders.notifyAll();
                }
                Pizza pizza = get();
                synchronized (storage) {
                    while (storage.isFull()) {
                        storage.wait();
                    }
                    storage.add(pizza);
                    storage.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
