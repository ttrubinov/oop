package ru.nsu.fit.trubinov;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final Queue<Pizza> pizzas = new LinkedList<>();
    private final int maxSize;

    public Storage(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        return pizzas.size() == maxSize;
    }

    public boolean isEmpty() {
        return pizzas.isEmpty();
    }

    public int size() {
        return pizzas.size();
    }

    public void add(Pizza pizza) {
        pizzas.add(pizza);
    }

    public Pizza take() {
        return pizzas.poll();
    }
}
