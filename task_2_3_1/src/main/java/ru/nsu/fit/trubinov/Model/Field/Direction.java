package ru.nsu.fit.trubinov.Model.Field;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public enum Direction {
    UP(0, -1, '^', new ArrayList<>(Arrays.asList(KeyType.ArrowUp, 'W', 'w'))),
    DOWN(0, 1, 'v', new ArrayList<>(Arrays.asList(KeyType.ArrowDown, 'S', 's'))),
    LEFT(-1, 0, '<', new ArrayList<>(Arrays.asList(KeyType.ArrowLeft, 'A', 'a'))),
    RIGHT(1, 0, '>', new ArrayList<>(Arrays.asList(KeyType.ArrowRight, 'D', 'd')));

    public final char headCharacter;
    private final int X;
    private final int Y;
    private final List<Object> keys;

    Direction(int X, int Y, char headCharacter, List<Object> keys) {
        this.X = X;
        this.Y = Y;
        this.headCharacter = headCharacter;
        this.keys = keys;
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
