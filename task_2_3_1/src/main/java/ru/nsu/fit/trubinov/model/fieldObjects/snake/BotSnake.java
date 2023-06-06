package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Coordinates;

/**
 * Bot snakes which has it own movement
 */
public class BotSnake extends Snake implements Cloneable {
    private Movement movement;

    public BotSnake(Coordinates coordinates, Field field) {
        super(coordinates, field);
        this.movement = Movement.NO_COLLISION;
    }

    public BotSnake(BotSnake botSnake, Movement movement) {
        super(botSnake);
        this.movement = movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    /**
     * Get new direction according to snake's movement.
     */
    public void getNewDirection() {
        movement.setNewDirection(this, this.getField());
    }

    @Override
    public BotSnake clone() {
        return new BotSnake(this, movement);
    }
}
