package ru.nsu.fit.trubinov;

public class Courier {
    private int deliveryTime;

    public Pizza get() {
        try {
            wait(deliveryTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Pizza();
    }
}
