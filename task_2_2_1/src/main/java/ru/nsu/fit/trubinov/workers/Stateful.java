package ru.nsu.fit.trubinov.workers;

import ru.nsu.fit.trubinov.state.State;

/**
 * Runnable entity with state.
 */
public interface Stateful extends Runnable {

    /**
     * State changing.
     *
     * @param state new state
     */
    void changeState(State state);
}
