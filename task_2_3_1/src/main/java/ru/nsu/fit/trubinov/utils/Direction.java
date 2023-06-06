package ru.nsu.fit.trubinov.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Possible snake directions.
 */
public enum Direction {
    UP(0, -1, 180),
    DOWN(0, 1, 0),
    LEFT(-1, 0, 90),
    RIGHT(1, 0, 270);

    public final int angle;
    private final int X;
    private final int Y;

    Direction(int X, int Y, int angle) {
        this.X = X;
        this.Y = Y;
        this.angle = angle;
    }

    /**
     * Get all possible directions randomly.
     *
     * @return list of all directions
     */
    public static List<Direction> getAllDirectionsRandomly() {
        List<Direction> allDirections = new ArrayList<>(Stream.of(Direction.values()).toList());
        Collections.shuffle(allDirections);
        return allDirections;
    }

    /**
     * Get random direction.
     *
     * @return random direction
     */
    public static Direction getRandomDirection() {
        return getAllDirectionsRandomly().get(0);
    }

    /**
     * Get shift by direction.
     *
     * @return coordinate shift
     */
    public Coordinates getShiftByDirection() {
        return new Coordinates(X, Y);
    }
}
