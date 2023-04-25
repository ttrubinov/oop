package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Coordinates;

public class BotSnake extends Snake {
    private final Movement movement;

    public BotSnake(Coordinates coordinates, Field field) {
        super(coordinates, field);
        this.movement = Movement.RANDOM_WITH_NO_COLLISION;
    }

    public BotSnake(Coordinates coordinates, Field field, Direction direction) {
        super(coordinates, field, direction);
        this.movement = Movement.STRAIGHT;
    }

    public void getNewDirection() {
        movement.setNewDirection(this, this.getField());
    }
}
