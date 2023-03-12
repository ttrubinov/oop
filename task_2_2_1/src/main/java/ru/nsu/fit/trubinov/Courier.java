package ru.nsu.fit.trubinov;

public class Courier implements Runnable {
    private int deliveryTime;
    private final Storage storage;

    public Courier(Storage storage) {
        this.storage = storage;
    }

    public Pizza get() {
        try {
            wait(deliveryTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Pizza();
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (storage) {
                    while (storage.isEmpty()) {
                        wait();
                    }
                    storage.take();
                    storage.notifyAll();
                    get();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
