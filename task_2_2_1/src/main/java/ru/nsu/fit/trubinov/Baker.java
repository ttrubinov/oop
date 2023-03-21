package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Baker implements Runnable {
    private final int id;
    private final int cookingTime;
    private Signal signal;
    private Orders orders;
    private Storage storage;

    public Baker(@JsonProperty("id") int id, @JsonProperty("cookingTime") int cookingTime) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.signal = null;
        this.orders = null;
        this.storage = null;
    }

    public Baker(int id, int cookingTime, Signal signal, Orders orders, Storage storage) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.signal = signal;
        this.orders = orders;
        this.storage = storage;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
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

    @Override
    public String toString() {
        return "Baker{" +
                "id=" + id +
                ", cookingTime=" + cookingTime +
                '}';
    }
}
