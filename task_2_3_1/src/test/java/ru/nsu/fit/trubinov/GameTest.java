package ru.nsu.fit.trubinov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.BotSnake;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Movement;
import ru.nsu.fit.trubinov.utils.FieldObject;

public class GameTest {
    private static Model model;

    @BeforeEach
    public void setup() {
        model = new Model(5, 5, 1, null);
        model.makeMove();
    }

    @Test
    void eatingTest() {
        model.makeMove();
        Assertions.assertEquals(3, (int) model.getBotSnakes().get(0).length);
    }

    @Test
    void movementTest() {
        BotSnake botSnake = model.getBotSnakes().get(0);
        botSnake.setMovement(Movement.RANDOM_WITH_NO_COLLISION);
        for (int i = 0; i < 5; i++) {
            model.makeMove();
        }
        Assertions.assertSame(model.getBotSnakes().get(0), botSnake);
        botSnake.setMovement(Movement.SMART);
        for (int i = 0; i < 100; i++) {
            model.makeMove();
        }
        Assertions.assertSame(model.getBotSnakes().get(0), botSnake);
    }

    @Test
    void fieldBordersTest() {
        for (int i = 0; i < model.field.getWidth(); i++) {
            for (int j = 0; j < model.field.getHeight(); j++) {
                if (i == 0 || j == 0 || i == model.field.getWidth() - 1 || j == model.field.getHeight() - 1) {
                    Assertions.assertEquals(FieldObject.WALL, model.field.getField()[i][j]);
                }
            }
        }
    }

    @Test
    void deathTest() {
        model.getBotSnakes().get(0).setMovement(Movement.STRAIGHT);
        for (int i = 0; i < 5; i++) {
            model.makeMove();
        }
        Assertions.assertEquals(0, model.getBotSnakes().size());
    }
}
