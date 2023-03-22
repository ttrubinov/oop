package ru.nsu.fit.trubinov.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.pizza.Pizza;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.signal.Signal;

@Slf4j
public class Baker implements Worker {
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
                        if (signal != Signal.Work) {
                            log.info("Baker №" + id + " finished his job");
                            return;
                        }
                        orders.wait();
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
                        if (signal == Signal.EmergencyInterrupt) {
                            log.info("Baker №" + id + " urgently finished his job");
                            return;
                        }
                        storage.wait();
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
