package ru.nsu.fit.trubinov.Model.FieldObjects;

import ru.nsu.fit.trubinov.Model.Field.Coordinates;
import ru.nsu.fit.trubinov.Model.Field.Direction;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final List<Coordinates> bodyCoordinates;
    public Integer length;
    private Direction direction;

    public Snake(Coordinates coordinates) {
        this.length = 1;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(coordinates);
        this.direction = Direction.getRandomDirection();
    }

    public Snake(List<Coordinates> bodyCoordinates, Direction direction) {
        this.bodyCoordinates = bodyCoordinates;
        this.length = bodyCoordinates.size();
        this.direction = direction;
    }

    public List<Coordinates> getBodyCoordinates() {
        return bodyCoordinates;
    }

    public Coordinates getHead() {
        return bodyCoordinates.get(bodyCoordinates.size() - 1);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        if (bodyCoordinates.size() == length) {
            bodyCoordinates.add(getHead().add(direction.getShiftByDirection()));
            bodyCoordinates.remove(0);
        } else if (bodyCoordinates.size() < length) {
            bodyCoordinates.add(getHead().add(direction.getShiftByDirection()));
        } else {
            bodyCoordinates.remove(0);
        }
    }

    public Snake possibleMove(Direction direction) {
        List<Coordinates> newBodyCoordinates = new ArrayList<>(bodyCoordinates);
        newBodyCoordinates.remove(0);
        newBodyCoordinates.add(getHead().add(direction.getShiftByDirection()));
        return new Snake(newBodyCoordinates, this.direction);
    }

    public Coordinates intersectsItself() {
        for (int i = 0; i < bodyCoordinates.size() - 1; i++) {
            for (int j = i + 1; j < bodyCoordinates.size(); j++) {
                if (bodyCoordinates.get(i).equals(bodyCoordinates.get(j))) {
                    return bodyCoordinates.get(i);
                }
            }
        }
        return null;
    }

    public Integer intersects(Snake snake) {
        for (int i = 0; i < snake.getBodyCoordinates().size(); i++) {
            if (this.getHead().equals(snake.getBodyCoordinates().get(i))) {
                return i;
            }
        }
        return null;
    }
}
