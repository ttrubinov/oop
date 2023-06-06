package ru.nsu.fit.trubinov.queues;

import ru.nsu.fit.trubinov.pizza.Pizza;

/**
 * Interface for synchronized queue
 */
public interface BlockingQueue {
    /**
     * Returns true if queue contains no elements.
     *
     * @return true if queue contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in queue.
     *
     * @return the number of elements in queue
     */
    int size();

    /**
     * Inserts the specified element into queue.
     * If the queue is full then waits for free space.
     *
     * @param pizza to insert into queue
     * @throws InterruptedException on interruption of current thread
     */
    void add(Pizza pizza) throws InterruptedException;

    /**
     * Retrieves and removes the head of this queue.
     * If the queue is empty then waits for an element.
     *
     * @throws InterruptedException on interruption of current thread
     */
    void take() throws InterruptedException;
}
