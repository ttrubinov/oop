package ru.nsu.fit.trubinov.courier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.signal.Signal;

@Slf4j
public class Courier implements Runnable {
    private final int id;
    private final int deliveryTime;
    private Signal signal;
    private Storage storage;

    public Courier(@JsonProperty("id") int id, @JsonProperty("deliveryTime") int deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
        this.signal = null;
        this.storage = null;
    }

    public Courier(int id, int deliveryTime, Signal signal, Storage storage) {
        this.id = id;
        this.deliveryTime = deliveryTime;
        this.signal = signal;
        this.storage = storage;
    }

    public void setSignal(Signal signal) {
        this.signal = signal;
    }

    public void setStorage(Storage storage) {
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

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", deliveryTime=" + deliveryTime +
                "}";
    }
}
