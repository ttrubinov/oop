package ru.nsu.fit.trubinov.queues;

import ru.nsu.fit.trubinov.pizza.Pizza;

public interface BlockingQueue {
    boolean isEmpty();

    int size();

    void add(Pizza pizza) throws InterruptedException;

    void take() throws InterruptedException;
}
