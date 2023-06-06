package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Coordinates;

public class BotSnake extends Snake implements Cloneable {
    private final Movement movement;

    public BotSnake(Coordinates coordinates, Field field) {
        super(coordinates, field);
        this.movement = Movement.SMART;
    }

    public BotSnake(BotSnake botSnake, Movement movement) {
        super(botSnake);
        this.movement = movement;
    }

    public void getNewDirection() {
        movement.setNewDirection(this, this.getField());
    }

    @Override
    public BotSnake clone() {
        return new BotSnake(this, movement);
    }
}
