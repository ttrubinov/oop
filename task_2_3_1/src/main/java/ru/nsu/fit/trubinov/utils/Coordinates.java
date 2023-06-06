package ru.nsu.fit.trubinov.utils;


import java.util.Objects;

/**
 * Coordinates on field grid.
 *
 * @param X X coordinate
 * @param Y Y coordinate
 */
public record Coordinates(int X, int Y) {
    @Override
    public int hashCode() {
        return Objects.hash(this.X, this.Y);
    }

    /**
     * Sum of two coordinates
     *
     * @param coordinates coordinates to add to current
     * @return result coordinates
     */
    public Coordinates sum(Coordinates coordinates) {
        return new Coordinates(this.X + coordinates.X, this.Y + coordinates.Y);
    }

    /**
     * Multiplying current coordinates by a value.
     *
     * @param n value to multiply current coordinates on
     * @return result coordinates
     */
    public Coordinates multiply(int n) {
        return new Coordinates(this.X * n, this.Y * n);
    }

    /**
     * Check if current coordinates is equal to another coordinates.
     *
     * @param coordinates coordinates to check equality with
     * @return true if current coordinates is equal to another coordinates.
     */
    public boolean equals(Coordinates coordinates) {
        return this.X == coordinates.X() && this.Y == coordinates.Y();
    }
}
