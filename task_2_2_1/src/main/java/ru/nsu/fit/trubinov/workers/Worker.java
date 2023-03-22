package ru.nsu.fit.trubinov.workers;

import ru.nsu.fit.trubinov.signal.Signal;

public interface Worker extends Runnable {

    void setSignal(Signal signal);
}
