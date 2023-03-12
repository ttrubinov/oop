package ru.nsu.fit.trubinov;

import java.util.LinkedList;
import java.util.Queue;

public class Orders {
    private final Queue<Pizza> orders = new LinkedList<>();

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public void add(Pizza pizza) {
        orders.add(pizza);
    }

    public void take() {
        orders.poll();
    }
}
