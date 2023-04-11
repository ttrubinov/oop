package ru.nsu.fit.trubinov.Model.Field;

public class Coordinates {
    private final int X;
    private final int Y;

    public Coordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Coordinates addCoordinates(Coordinates coordinates) {
        return new Coordinates(this.X + coordinates.X, this.Y + coordinates.Y);
    }

    public boolean equals(Coordinates coordinates) {
        return this.X == coordinates.getX() && this.Y == coordinates.getY();
    }
}
