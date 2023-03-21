package ru.nsu.fit.trubinov;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Courier implements Runnable {
    private final int id;
    private final int deliveryTime;
    private Signal signal;
    private final Storage storage;

    public Courier(int id, Signal signal, int deliveryTime, Storage storage) {
        this.id = id;
        this.signal = signal;
        this.deliveryTime = deliveryTime;
        this.storage = storage;
    }

    public void changeWorkingType(Signal signal) {
        this.signal = signal;
    }

    public void get() {
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(1000L * deliveryTime);
            }
            if (signal == Signal.EmergencyInterrupt) {
                return;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Courier №" + id + " delivered a pizza");
        try {
            synchronized (this) {
                Thread.sleep(1000L * deliveryTime);
            }
            if (signal == Signal.EmergencyInterrupt) {
                return;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Courier №" + id + " came back to a storage");
    }

    @Override
    public void run() {
        while (signal != Signal.EmergencyInterrupt && !(signal == Signal.Finish && storage.isEmpty())) {
            try {
                synchronized (storage) {
                    while (storage.isEmpty()) {
                        storage.wait();
                        if (signal == Signal.EmergencyInterrupt) {
                            return;
                        }
                    }
                    storage.take();
                    log.info("Courier №" + id + " took pizza from storage, there are " +
                            storage.size() + " pizzas left");
                    storage.notifyAll();
                }
                get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Courier №" + id + " finished his job");
    }
}
