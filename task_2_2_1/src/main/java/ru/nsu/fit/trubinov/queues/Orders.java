package ru.nsu.fit.trubinov.queues;

import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.pizza.Pizza;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Pizzeria synchronized orders for client and baker interaction.
 */
@Slf4j
public class Orders implements BlockingQueue {
    private final Queue<Pizza> orders = new LinkedList<>();

    public boolean isEmpty() {
        synchronized (orders) {
            return orders.isEmpty();
        }
    }

    public int size() {
        synchronized (orders) {
            return orders.size();
        }
    }

    public void add(Pizza pizza) {
        synchronized (orders) {
            orders.add(pizza);
            orders.notifyAll();
        }
    }

    public void take() throws InterruptedException {
        synchronized (orders) {
            while (isEmpty()) {
                orders.wait();
            }
            orders.poll();
            orders.notifyAll();
        }
    }
}
