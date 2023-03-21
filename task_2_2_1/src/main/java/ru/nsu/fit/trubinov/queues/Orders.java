package ru.nsu.fit.trubinov.queues;

import ru.nsu.fit.trubinov.pizza.Pizza;

import java.util.LinkedList;
import java.util.Queue;

public class Orders {
    private final Queue<Pizza> orders = new LinkedList<>();

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public int size() {
        return orders.size();
    }

    public void add(Pizza pizza) {
        orders.add(pizza);
    }

    public void take() {
        orders.poll();
    }
}
