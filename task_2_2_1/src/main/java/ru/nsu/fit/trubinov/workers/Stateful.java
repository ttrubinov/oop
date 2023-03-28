package ru.nsu.fit.trubinov.workers;

import ru.nsu.fit.trubinov.state.State;

public interface Stateful extends Runnable {

    void changeState(State state);
}
