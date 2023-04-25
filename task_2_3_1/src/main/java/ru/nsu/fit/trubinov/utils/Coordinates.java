package ru.nsu.fit.trubinov.utils;


public record Coordinates(int X, int Y) {
    public Coordinates sum(Coordinates coordinates) {
        return new Coordinates(this.X + coordinates.X, this.Y + coordinates.Y);
    }

    public boolean equals(Coordinates coordinates) {
        return this.X == coordinates.X() && this.Y == coordinates.Y();
    }
}
