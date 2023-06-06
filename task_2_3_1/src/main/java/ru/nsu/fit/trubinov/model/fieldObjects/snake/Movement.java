package ru.nsu.fit.trubinov.model.fieldObjects.snake;

import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.utils.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Different bot movements.
 */
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

    /**
     * Movement, in which snake moves by straight line but tries not to collide on this step.
     *
     * @param botSnake snake to do movement with
     * @param field    field of the snake
     */
    private static void doNoCollisionMovement(BotSnake botSnake, Field field) {
        List<Direction> randomDirections = Direction.getAllDirectionsRandomly();
        for (Direction randomDirection : randomDirections) {
            if (!botSnake.isPossibleTurn(randomDirection) || !botSnake.getField().deathIntersection(botSnake)) {
                continue;
            }
            botSnake.setDirection(randomDirection);
            if (botSnake.getField().deathIntersectionCoordinates(botSnake) == null) {
                break;
            }
        }
    }

    /**
     * Movement, in which snake moves randomly but tries not to collide on this step.
     *
     * @param botSnake snake to do movement with
     * @param field    field of the snake
     */
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

    /**
     * Movement, in which snake moves by straight line
     *
     * @param botSnake snake to do movement with
     * @param field    field of the snake
     */
    private static void doStraightMovement(BotSnake botSnake, Field field) {
    }

    /**
     * Movement, in which snake calculates N steps further and chooses the best one.
     * It has adjustable recursion depth, depending on calculation time of previous calculation.
     *
     * @param botSnake snake to do movement with
     * @param field    field of the snake
     */
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

    /**
     * Method of calculating N steps of recursion for smart movement.
     *
     * @param botSnake snake, which steps to calculate
     * @param n        steps of recursion
     * @return maximal value that snake can get in N steps
     */
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

    /**
     * Evaluation of position of the snake.
     * Snake gets more score with apples and following walls with its body lines.
     *
     * @param botSnake snake to evaluate
     * @return evaluation value
     */
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

    /**
     * Get all the movements in random order.
     *
     * @return list of movements
     */
    public static List<Movement> getAllMovementsRandomly() {
        List<Movement> allMovements = new ArrayList<>(Stream.of(Movement.values()).toList());
        Collections.shuffle(allMovements);
        return allMovements;
    }

    /**
     * Get random movement.
     *
     * @return random movement
     */
    public static Movement getRandomMovement() {
        return getAllMovementsRandomly().get(0);
    }

    public void setNewDirection(BotSnake botSnake, Field field) {
        movementFunction.accept(botSnake, field);
    }
}
