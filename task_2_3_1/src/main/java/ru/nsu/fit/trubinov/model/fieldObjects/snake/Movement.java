package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.field.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

// TODO: More movement types
public enum Movement {
    NO_COLLISION(Movement::doNoCollisionMovement),
    RANDOM_WITH_NO_COLLISION(Movement::doRandomNoCollisionMovement),
    STRAIGHT(Movement::doStraightMovement);

    private final BiConsumer<BotSnake, Field> function;

    Movement(BiConsumer<BotSnake, Field> getNewDirection) {
        this.function = getNewDirection;
    }

    private static void doNoCollisionMovement(BotSnake botSnake, Field field) {
        List<Direction> randomDirections = Direction.getAllDirectionsRandomly();
        while (randomDirections.size() > 0 && botSnake.getField().deathIntersectionCoordinates(botSnake) != null) {
            if (!botSnake.isPossibleTurn(randomDirections.get(0))) {
                continue;
            }
            botSnake.setDirection(randomDirections.get(0));
            randomDirections.remove(0);
        }
        botSnake.setDirection(botSnake.getDirection());
    }

    private static void doRandomNoCollisionMovement(BotSnake botSnake, Field field) {
        List<Direction> randomDirections = Direction.getAllDirectionsRandomly();
        for (Direction randomDirection : randomDirections) {
            if (!botSnake.isPossibleTurn(randomDirection)) {
                continue;
            }
            botSnake.setDirection(randomDirection);
            if (botSnake.getField().deathIntersectionCoordinates(botSnake) == null) {
                break;
            }
        }
    }

    private static void doStraightMovement(BotSnake botSnake, Field field) {

    }

    public static List<Movement> getAllMovementsRandomly() {
        List<Movement> allMovements = new ArrayList<>(Stream.of(Movement.values()).toList());
        Collections.shuffle(allMovements);
        return allMovements;
    }

    public static Movement getRandomMovement() {
        return getAllMovementsRandomly().get(0);
    }

    public void setNewDirection(BotSnake botSnake, Field field) {
        function.accept(botSnake, field);
    }
}
