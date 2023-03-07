package ru.nsu.fit.trubinov;

public class Baker {
    private int cookingTime;

    public Pizza get() {
        try {
            wait(cookingTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Pizza();
    }
}
