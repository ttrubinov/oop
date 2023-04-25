package ru.nsu.fit.trubinov.model.fieldObjects;

import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class Snake implements FieldObject {
    private final List<Coordinates> bodyCoordinates;
    private final Field field;
    public Integer length;
    private Direction direction;

    public Snake(Coordinates coordinates, Field field) {
        this.length = 2;
        this.field = field;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(coordinates);
        field.addFieldObjectWithCollision(coordinates);
        this.direction = Direction.getRandomDirection();
    }

    public Snake(Coordinates coordinates, Field field, Direction direction) {
        this.length = 2;
        this.field = field;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(coordinates);
        field.addFieldObjectWithCollision(coordinates);
        this.direction = direction;
    }

    public Snake(List<Coordinates> bodyCoordinates, Field field, Direction direction) {
        this.field = field;
        this.bodyCoordinates = new ArrayList<>();
        this.bodyCoordinates.addAll(bodyCoordinates);
        bodyCoordinates.forEach(field::addFieldObjectWithCollision);
        this.length = bodyCoordinates.size();
        this.direction = direction;
    }

    public List<Coordinates> getCoordinates() {
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

    public Coordinates getNextHeadPosition() {
        return getHead().sum(direction.getShiftByDirection());
    }

    public void move() {
        if (bodyCoordinates.size() == length) {
            bodyCoordinates.add(this.getNextHeadPosition());
            field.addFieldObjectWithCollision(this.getHead());
            field.addEmptyCell(bodyCoordinates.get(0));
            bodyCoordinates.remove(0);
        } else if (bodyCoordinates.size() < length) {
            bodyCoordinates.add(this.getNextHeadPosition());
            field.addFieldObjectWithCollision(this.getHead());
        }
    }
}
