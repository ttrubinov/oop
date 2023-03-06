package ru.nsu.fit.trubinov;

import java.util.LinkedList;
import java.util.Queue;

public class Orders {
    private final Queue<Pizza> orders = new LinkedList<>();
    private int maxSize;

    public boolean isFull() {
        return !orders.isEmpty();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public void add(Pizza pizza) {
        orders.add(pizza);
    }

    public Pizza take() {
        return orders.poll();
    }
}
