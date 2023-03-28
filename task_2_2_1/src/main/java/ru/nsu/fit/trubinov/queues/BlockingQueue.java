package ru.nsu.fit.trubinov.queues;

import ru.nsu.fit.trubinov.pizza.Pizza;

public interface BlockingQueue {
    public boolean isEmpty();

    public int size();

    public void add(Pizza pizza) throws InterruptedException;

    public void take() throws InterruptedException;
}
