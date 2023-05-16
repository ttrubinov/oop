package ru.nsu.fit.trubinov.utils;


import java.util.Objects;

public record Coordinates(int X, int Y) {
    public static int getAngle(Coordinates coords1, Coordinates coords2) {
        if (coords1.Y == coords2.Y) {
            if (coords1.X > coords2.X) {
                return 90;
            } else return 270;
        } else {
            if (coords1.Y > coords2.Y) {
                return 180;
            } else return 0;
        }
    }

    public static Integer getAngle2(Coordinates coords1, Coordinates coords2, Coordinates coordsMid) {
        if (coords1.Y == coords2.Y || coords1.X == coords2.X) {
            return null;
        }
        if (coords1.X > coords2.X && coords1.Y < coords2.Y && coordsMid.Y != coords2.Y) {
            return 0;
        }
        if (coords1.X > coords2.X && coords1.Y < coords2.Y) {
            return 180;
        }
        if (coords1.X < coords2.X && coords1.Y < coords2.Y && coordsMid.Y == coords2.Y) {
            return 270;
        }
        if (coords1.X < coords2.X && coords1.Y < coords2.Y) {
            return 90;
        }
        if (coords1.X < coords2.X && coordsMid.Y != coords2.Y) {
            return 180;
        }
        if (coords1.X < coords2.X) {
            return 0;
        }
        if (coordsMid.Y == coords2.Y) {
            return 90;
        } else {
            return 270;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.X, this.Y);
    }

    public Coordinates sum(Coordinates coordinates) {
        return new Coordinates(this.X + coordinates.X, this.Y + coordinates.Y);
    }

    public Coordinates subtract(Coordinates coordinates) {
        return new Coordinates(this.X - coordinates.X, this.Y - coordinates.Y);
    }

    public Coordinates multiply(int n) {
        return new Coordinates(this.X * n, this.Y * n);
    }

    public boolean equals(Coordinates coordinates) {
        return this.X == coordinates.X() && this.Y == coordinates.Y();
    }
}
