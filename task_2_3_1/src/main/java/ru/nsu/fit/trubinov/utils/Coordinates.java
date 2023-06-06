package ru.nsu.fit.trubinov.utils;


import java.util.Objects;

public record Coordinates(int X, int Y) {

    @Override
    public int hashCode() {
        return Objects.hash(this.X, this.Y);
    }

    public Coordinates sum(Coordinates coordinates) {
        return new Coordinates(this.X + coordinates.X, this.Y + coordinates.Y);
    }

    public Coordinates multiply(int n) {
        return new Coordinates(this.X * n, this.Y * n);
    }

    public boolean equals(Coordinates coordinates) {
        return this.X == coordinates.X() && this.Y == coordinates.Y();
    }
}
