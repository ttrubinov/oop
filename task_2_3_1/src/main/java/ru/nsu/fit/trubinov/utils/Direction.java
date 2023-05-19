package ru.nsu.fit.trubinov.utils;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    UP(0, -1, '^', new ArrayList<>(Arrays.asList(KeyType.ArrowUp, 'W', 'w')), 180),
    DOWN(0, 1, 'v', new ArrayList<>(Arrays.asList(KeyType.ArrowDown, 'S', 's')), 0),
    LEFT(-1, 0, '<', new ArrayList<>(Arrays.asList(KeyType.ArrowLeft, 'A', 'a')), 90),
    RIGHT(1, 0, '>', new ArrayList<>(Arrays.asList(KeyType.ArrowRight, 'D', 'd')), 270);

    public final char headCharacter;
    public final int angle;
    private final int X;
    private final int Y;
    private final List<Object> keys;

    Direction(int X, int Y, char headCharacter, List<Object> keys, int angle) {
        this.X = X;
        this.Y = Y;
        this.headCharacter = headCharacter;
        this.keys = keys;
        this.angle = angle;
    }

    public static Direction getDirectionByKey(KeyStroke keyStroke) {
        if (keyStroke == null) {
            return null;
        }
        for (Direction direction : getAllDirectionsRandomly()) {
            if (direction.keys.contains(keyStroke.getKeyType()) || direction.keys.contains(keyStroke.getCharacter())) {
                return direction;
            }
        }
        return null;
    }

    public static List<Direction> getAllDirectionsRandomly() {
        List<Direction> allDirections = new ArrayList<>(Stream.of(Direction.values()).toList());
        Collections.shuffle(allDirections);
        return allDirections;
    }

    public static Direction getRandomDirection() {
        return getAllDirectionsRandomly().get(0);
    }

    public Coordinates getShiftByDirection() {
        return new Coordinates(X, Y);
    }
}
