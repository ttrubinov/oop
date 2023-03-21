package ru.nsu.fit.trubinov;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Baker implements Runnable {

    private final int id;
    private final int cookingTime;
    private Signal signal;
    private final Orders orders;
    private final Storage storage;

    public Baker(int id, Signal signal, int cookingTime, Orders orders, Storage storage) {
        this.id = id;
        this.signal = signal;
        this.cookingTime = cookingTime;
        this.orders = orders;
        this.storage = storage;
    }

    public void changeWorkingType(Signal signal) {
        this.signal = signal;
    }

    public Pizza get() {
        try {
            synchronized (Thread.currentThread()) {
                Thread.currentThread().wait(1000L * cookingTime);
            }
            if (signal == Signal.EmergencyInterrupt) {
                return null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Pizza();
    }

    @Override
    public void run() {
        while (signal != Signal.EmergencyInterrupt && !(signal == Signal.Finish && orders.isEmpty())) {
            try {
                synchronized (orders) {
                    while (orders.isEmpty()) {
                        orders.wait();
                        if (signal != Signal.Work) {
                            log.info("Baker №" + id + " finished his job");
                            return;
                        }
                    }
                    orders.take();
                    log.info("Baker №" + id + " took order and started baking pizza, there are " +
                            orders.size() + " orders left");
                    orders.notifyAll();
                }
                Pizza pizza = get();
                if (signal == Signal.EmergencyInterrupt) {
                    log.info("Baker №" + id + " urgently finished his job");
                    return;
                }
                synchronized (storage) {
                    while (storage.isFull()) {
                        log.info("Baker №" + id + " cooked pizza, but storage is full");
                        storage.wait();
                        if (signal == Signal.EmergencyInterrupt) {
                            log.info("Baker №" + id + " urgently finished his job");
                            return;
                        }
                    }
                    storage.add(pizza);
                    log.info("Baker №" + id + " cooked pizza and put it into storage, there are " +
                            storage.size() + " pizzas in storage");
                    storage.notifyAll();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Baker №" + id + " finished his job");
    }
}
