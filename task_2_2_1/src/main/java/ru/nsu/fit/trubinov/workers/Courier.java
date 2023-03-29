package ru.nsu.fit.trubinov.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.state.State;

@Slf4j
public class Courier implements Stateful {
    private final int id;
    private final int deliveryTime;
    private State state;
    private Storage storage;

    public Courier(@JsonProperty("id") int id, @JsonProperty("deliveryTime") int deliveryTime) {
        this.id = id;
        this.deliveryTime = deliveryTime;
        this.state = null;
        this.storage = null;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void get() throws InterruptedException {
        Thread.sleep(1000L * deliveryTime);
        log.info("Courier №" + id + " delivered a pizza");
        Thread.sleep(1000L * deliveryTime);
        log.info("Courier №" + id + " came back to a storage");
    }

    @Override
    public void run() {
        try {
            while (state != State.EmergencyInterrupt && !(state == State.Finish && storage.isEmpty())) {
                storage.take();
                log.info("Courier №" + id + " took pizza from storage, there are " +
                        storage.size() + " pizzas left");
                get();
            }
            log.info("Courier №" + id + " finished his job");
        } catch (InterruptedException e) {
            log.info("Courier №" + id + " urgently finished his job");
        }
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", deliveryTime=" + deliveryTime +
                "}";
    }
}
