package ru.nsu.fit.trubinov;

import java.util.LinkedList;
import java.util.Queue;

public class Storage {
    private final Queue<Pizza> pizzas = new LinkedList<>();
    private int maxSize;

    public boolean isFull() {
        return !pizzas.isEmpty();
    }

    public boolean isEmpty() {
        return pizzas.isEmpty();
    }

    public void add(Pizza pizza) {
        pizzas.add(pizza);
    }

    public Pizza take() {
        return pizzas.poll();
    }
}
