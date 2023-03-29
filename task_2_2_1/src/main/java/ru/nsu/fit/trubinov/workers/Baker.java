package ru.nsu.fit.trubinov.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.pizza.Pizza;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.queues.Storage;
import ru.nsu.fit.trubinov.state.State;

/**
 * Baker of pizzeria.
 */
@Slf4j
public class Baker implements Stateful {
    private final int id;
    private final int cookingTime;
    private State state;
    private Orders orders;
    private Storage storage;

    public Baker(@JsonProperty("id") int id, @JsonProperty("cookingTime") int cookingTime) {
        this.id = id;
        this.cookingTime = cookingTime;
        this.state = null;
        this.orders = null;
        this.storage = null;
    }

    public void changeState(State state) {
        this.state = state;
    }

    /**
     * Bind orders queue to current baker.
     *
     * @param orders queue to bind.
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    /**
     * Bind storage to current baker.
     *
     * @param storage storage to bind.
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Cycle of baker's work.
     * Baker immediately stops working on Emergency state
     * and stops working after finishing all the orders on Finish state.
     */
    @Override
    public void run() {
        while (state != State.EmergencyInterrupt && !(state == State.Finish && orders.isEmpty())) {
            try {
                orders.take();
                log.info("Baker №" + id + " took order and started baking pizza, there are " +
                        orders.size() + " orders left");
                Thread.sleep(1000L * cookingTime);
                storage.add(new Pizza());
                log.info("Baker №" + id + " cooked pizza and put it into storage, there are " +
                        storage.size() + " pizzas in storage");
            } catch (InterruptedException e) {
                log.info("Baker №" + id + " urgently finished his job");
                return;
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
