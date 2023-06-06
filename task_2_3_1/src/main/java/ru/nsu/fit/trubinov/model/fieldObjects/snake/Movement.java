package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public enum Movement {
    NO_COLLISION(Movement::doNoCollisionMovement),
    RANDOM_WITH_NO_COLLISION(Movement::doRandomNoCollisionMovement),
    STRAIGHT(Movement::doStraightMovement),
    SMART(Movement::doSmartMovement);

    public static int recursionDepth = 8;
    private static Direction newDirection;
    private final BiConsumer<BotSnake, Field> movementFunction;

    Movement(BiConsumer<BotSnake, Field> getNewDirection) {
        this.movementFunction = getNewDirection;
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

    private static void doSmartMovement(BotSnake botSnake, Field field) {
        newDirection = null;
        long startTime = System.currentTimeMillis();
        calculateN(botSnake.clone(), 0);
        long endTime = System.currentTimeMillis();
        if (endTime - startTime < 10 && recursionDepth < 60) {
            recursionDepth++;
        } else {
            recursionDepth--;
        }
        if (newDirection != null) {
            botSnake.setDirection(newDirection);
        }
    }

    private static int calculateN(BotSnake botSnake, int n) {
        if (n == recursionDepth) {
            return evaluateNextPos(botSnake);
        }
        int maxValue = Integer.MIN_VALUE;
        Direction newDir = null;
        Direction initialDir = botSnake.getDirection();
        for (Direction direction : Direction.getAllDirectionsRandomly()) {
            if (!botSnake.isPossibleTurn(direction)) {
                continue;
            }
            botSnake.setDirection(direction);
            if (botSnake.getField().deathIntersection(botSnake)) {
                botSnake.setDirection(initialDir);
                continue;
            }
            botSnake.setDirection(initialDir);
            BotSnake curBotSnake = botSnake.clone();
            curBotSnake.setDirection(direction);
            int curSnakeValue = evaluateNextPos(curBotSnake);
            if (curSnakeValue < 0) {
                continue;
            }
            curBotSnake.move();
            curSnakeValue += calculateN(curBotSnake, n + 1);
            if (maxValue < curSnakeValue) {
                maxValue = curSnakeValue;
                newDir = curBotSnake.getDirection();
            }
        }
        if (n == 0 && newDir != null) {
            newDirection = newDir;
        }
        return maxValue;
    }

    private static int evaluateNextPos(BotSnake botSnake) {
        int curValue = 0;
        final int appleValue = 30;
        final int wallValue = -1000;
        final int snakeIsCloseValue = 14;
        final int wallIsCloseValue = snakeIsCloseValue * 2;
        if (botSnake.getField().intersectionWithApple(botSnake)) {
            curValue += appleValue;
            botSnake.length++;
        } else if (botSnake.getField().deathIntersection(botSnake)) {
            curValue -= wallValue;
        } else {
            curValue++;
        }
        Direction initialDir = botSnake.getDirection();
        for (Direction direction : Direction.getAllDirectionsRandomly()) {
            botSnake.setDirection(direction);
            if (botSnake.getField().isSnakeIntersection(botSnake)) {
                curValue += snakeIsCloseValue;
            } else if (botSnake.getField().isWallIntersection(botSnake)) {
                curValue += wallIsCloseValue;
            }
        }
        botSnake.setDirection(initialDir);
        return curValue;
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
        movementFunction.accept(botSnake, field);
    }
}
