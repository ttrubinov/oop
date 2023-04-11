package ru.nsu.fit.trubinov.Model.FieldObjects;

import ru.nsu.fit.trubinov.Model.Field.Coordinates;
import ru.nsu.fit.trubinov.Model.Field.Direction;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Coordinates> bodyCoordinates;
    public Integer length;
    private Direction direction;

    public Snake() {
        this.length = 1;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(new Coordinates(0, 0));
        this.direction = Direction.DOWN;
    }

    public ArrayList<Coordinates> getBodyCoordinates() {
        return bodyCoordinates;
    }

    public Coordinates getHead() {
        return bodyCoordinates.get(bodyCoordinates.size() - 1);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void move() {
        bodyCoordinates.add(getHead().addCoordinates(direction.getShiftByDirection()));
        if (length == bodyCoordinates.size()) {
            bodyCoordinates.remove(0);
        }
    }

    public boolean intersectsItself() {
        for (int i = 0; i < bodyCoordinates.size() - 1; i++) {
            for (int j = i + 1; j < bodyCoordinates.size(); j++) {
                if (bodyCoordinates.get(i).equals(bodyCoordinates.get(j))) {
                    return true;
                }
            }
        }
        return false;
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
