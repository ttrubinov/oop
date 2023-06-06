package ru.nsu.fit.trubinov.workers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.pizza.Pizza;
import ru.nsu.fit.trubinov.queues.Orders;
import ru.nsu.fit.trubinov.state.State;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Client of pizzeria.
 */
@Slf4j
public class Client implements Stateful {
    private final int id;
    private final int minTimeBetweenOrders;
    private final int maxTimeBetweenOrders;
    private Orders orders;
    private State state;

    public Client(@JsonProperty("id") int id,
                  @JsonProperty("minTimeBetweenOrders") int minTimeBetweenOrders,
                  @JsonProperty("maxTimeBetweenOrders") int maxTimeBetweenOrders) {
        this.id = id;
        this.minTimeBetweenOrders = minTimeBetweenOrders;
        this.maxTimeBetweenOrders = maxTimeBetweenOrders;
        this.state = null;
        this.orders = null;
    }

    public void changeState(State state) {
        this.state = state;
    }

    /**
     * Bind orders queue to current client.
     *
     * @param orders queue to bind.
     */
    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    /**
     * Cycle of client's orders.
     * Client immediately stops doing orders on Emergency or Finish state.
     */
    @Override
    public void run() {
        try {
            while (state == State.Work) {
                Thread.sleep(1000L * ThreadLocalRandom.current().nextInt(minTimeBetweenOrders, maxTimeBetweenOrders + 1));
                if (state != State.Work) {
                    return;
                }
                orders.add(new Pizza());
                log.info("Client №" + id + " ordered a pizza, there are " + orders.size() + " orders");
            }
        } catch (InterruptedException ignored) {
        } finally {
            log.info("Client №" + id + " stopped doing orders");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", minTimeBetweenOrders=" + minTimeBetweenOrders +
                ", maxTimeBetweenOrders=" + maxTimeBetweenOrders +
                '}';
    }
}
