package ru.nsu.fit.trubinov.queues;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.fit.trubinov.pizza.Pizza;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class Storage implements BlockingQueue {
    private final Queue<Pizza> storage = new LinkedList<>();
    private final int maxSize;

    public Storage(@JsonProperty("maxSize") int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        synchronized (storage) {
            return storage.size() == maxSize;
        }
    }

    public boolean isEmpty() {
        synchronized (storage) {
            return storage.isEmpty();
        }
    }

    public int size() {
        synchronized (storage) {
            return storage.size();
        }
    }

    public void add(Pizza pizza) throws InterruptedException {
        synchronized (storage) {
            while (isFull()) {
                storage.wait();
            }
            storage.add(pizza);
            storage.notifyAll();
        }
    }

    public void take() throws InterruptedException {
        synchronized (storage) {
            while (isEmpty()) {
                storage.wait();
            }
            storage.poll();
            storage.notifyAll();
        }
    }
}
