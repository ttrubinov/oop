package ru.nsu.fit.trubinov.Model.Field;

public enum Direction {
    UP(0, 1), DOWN(0, -1), LEFT(-1, 0), RIGHT(1, 0);
    private final int X;
    private final int Y;

    Direction(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public Coordinates getShiftByDirection() {
        return new Coordinates(X, Y);
    }
}
