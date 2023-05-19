package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.model.fieldObjects.FieldObject;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.utils.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Snake implements FieldObject {
    public final Map<Coordinates, Direction> snakeCeilDirections;
    private final List<Coordinates> bodyCoordinates;
    public Field field;
    public Integer length;
    private Direction direction;

    public Snake(Coordinates coordinates, Field field) {
        this.length = 2;
        this.field = field;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(coordinates);
        field.addSnake(coordinates);
        this.direction = Direction.getRandomDirection();
        snakeCeilDirections = new HashMap<>();
        snakeCeilDirections.put(coordinates, this.direction);
    }

    public Snake(Coordinates coordinates, Field field, Direction direction) {
        this.length = 2;
        this.field = field;
        this.bodyCoordinates = new ArrayList<>();
        bodyCoordinates.add(coordinates);
        field.addSnake(coordinates);
        this.direction = direction;
        snakeCeilDirections = new HashMap<>();
        snakeCeilDirections.put(coordinates, this.direction);
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

    public Direction getSnakeCeilDirection(Coordinates ceil) {
        return snakeCeilDirections.get(ceil);
    }

    public Coordinates getNextHeadPosition() {
        return getHead().sum(direction.getShiftByDirection());
    }

    public Field getField() {
        return field;
    }


    /**
     * Snake movement by deleting 1 cell from a tail and adding 1 cell in front of head.
     * If actual snake length is less that it should be, then deleting 1 cell from a tail doesn't happen.
     * If actual snake length is more that it should be, then adding 1 cell in front of head doesn't happen.
     */
    public void move() {
        if (bodyCoordinates.size() == 1) {
//            field.addFieldObjectWithCollision(bodyCoordinates.get(0));
            field.addSnake(bodyCoordinates.get(0));
        }
        if (bodyCoordinates.size() == length) { // Standard movement
            bodyCoordinates.add(this.getNextHeadPosition());
            snakeCeilDirections.remove(bodyCoordinates.get(0));
            snakeCeilDirections.put(this.getHead(), this.getDirection());
            field.addEmptyCell(bodyCoordinates.get(0));
            field.addSnake(this.getHead());
            bodyCoordinates.remove(0);
        } else if (bodyCoordinates.size() < length) { // Snake collected an apple or bit off a tail of other snake
            bodyCoordinates.add(this.getNextHeadPosition());
            snakeCeilDirections.put(this.getHead(), this.getDirection());
            field.addSnake(this.getHead());
        } else { // Any snake bit off a tail of this snake, so length decreased
            field.addEmptyCell(bodyCoordinates.get(0));
            snakeCeilDirections.remove(bodyCoordinates.get(0));
            bodyCoordinates.remove(0);
        }
    }

    /**
     * Check if snake can do a turn in the direction (snake can't turn 180°).
     *
     * @param direction Direction to turn
     * @return true if snake can do a turn in the direction
     */
    public boolean isPossibleTurn(Direction direction) {
        return this.getCoordinates().size() <= 1 ||
                !this.getHead().sum(direction.getShiftByDirection()).
                        equals(this.getCoordinates().get(this.getCoordinates().size() - 2));
    }

    @Override
    public String toString() {
        return "Snake{" +
                "bodyCoordinates=" + bodyCoordinates +
                ", length=" + length +
                ", direction=" + direction +
                '}';
    }
}
