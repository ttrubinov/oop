package ru.nsu.fit.trubinov.model;

import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.field.Field;
import ru.nsu.fit.trubinov.model.fieldObjects.Apple;
import ru.nsu.fit.trubinov.model.fieldObjects.Wall;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.BotSnake;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    private final Snake userSnake;
    private final List<BotSnake> botSnakes;
    private final List<Snake> allSnakes;
    private final List<Wall> walls;
    private final List<Apple> apples;
    private final Field field;
    private final int difficultyLevel;
    private final int botsCount;

    public Model(int width, int height, int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        botsCount = difficultyLevel;
        field = new Field(width, height);
        userSnake = new Snake(new Coordinates(width / 2, height / 2), field, Direction.RIGHT);
        botSnakes = new ArrayList<>();
        spawnBots();
        allSnakes = new ArrayList<>();
        allSnakes.add(userSnake);
        allSnakes.addAll(botSnakes);
        walls = new ArrayList<>();
        apples = new ArrayList<>();
    }

    public Coordinates getMaxCoordinates() {
        return field.getWidthAndHeight();
    }

    public Snake getUserSnake() {
        return userSnake;
    }

    public List<BotSnake> getBotSnakes() {
        return botSnakes;
    }

    public List<Apple> getApples() {
        return apples;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Coordinates makeMove() {
        collectApples(userSnake);
        moveBots();
        Coordinates intersection = deathIntersectionCoordinates(userSnake);
        userSnake.move();
        if (intersection != null) {
            return intersection;
        }
        allSnakes.removeIf(snake -> snake.length <= 0);
        spawnWalls();
        spawnApples();
        spawnBots();
        System.out.println(field);
        return null;
    }

    private void moveBots() {
        List<BotSnake> botsToDelete = new ArrayList<>();
        Iterator<BotSnake> iterator = botSnakes.iterator();
        while (iterator.hasNext()) {
            BotSnake botSnake = iterator.next();
            botSnake.getNewDirection();
            Coordinates botIntersection = deathIntersectionCoordinates(botSnake);
            collectApples(botSnake);
            botSnake.move();
            if (botIntersection != null) {
                botSnake.getCoordinates().forEach(field::addEmptyCell);
                field.addFieldObjectWithCollision(botSnake.getHead());
                iterator.remove();
                botsToDelete.add(botSnake);
            }
        }
        botSnakes.removeAll(botsToDelete);
        allSnakes.removeAll(botsToDelete);
    }

    private void spawnBots() {
        if ((double) botSnakes.size() >= botsCount) {
            return;
        }
        List<Coordinates> emptyCells = field.getAllEmptyCells(2);
        while ((double) botSnakes.size() < botsCount && emptyCells.size() > 0) {
            botSnakes.add(new BotSnake(emptyCells.get(0), field));
            field.addFieldObjectWithCollision(emptyCells.get(0));
            emptyCells.remove(0);
            emptyCells = field.getAllEmptyCells(2);
        }
    }

    private void collectApples(Snake snake) {
        if (field.intersectionWithApple(snake)) {
            snake.length++;
        }
        apples.stream().filter(apple ->
                snake.getNextHeadPosition().equals(apple.coordinates())).findFirst().ifPresent(apples::remove);
    }

    private Coordinates deathIntersectionCoordinates(Snake snake) {
        return field.deathIntersectionCoordinates(snake);
    }

    private void spawnApples() {
        if ((double) apples.size() >= Math.sqrt(field.size()) / (difficultyLevel)) {
            return;
        }
        List<Coordinates> emptyCells = field.getAllEmptyCells(0);
        while ((double) apples.size() < Math.sqrt(field.size()) / (difficultyLevel) && emptyCells.size() > 0) {
            if (!field.isEmpty(emptyCells.get(0))) {
                continue;
            }
            apples.add(new Apple(emptyCells.get(0)));
            field.addNoCollisionFieldObject(emptyCells.get(0));
            emptyCells.remove(0);
            emptyCells = field.getAllEmptyCells(0);
        }
    }

    private void spawnWalls() {
        int maxDifficultyLevel = 11;
        int neededWallsSize = field.size() / 10 / (maxDifficultyLevel - difficultyLevel);
        if (walls.size() >= neededWallsSize) {
            return;
        }
        List<Coordinates> emptyCells = field.getAllEmptyCells(1);
        while (walls.size() < neededWallsSize && emptyCells.size() > 0) {
            int wallSize = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            List<Coordinates> wallCoordinates = new ArrayList<>();
            for (int i = 0; i < wallSize; i++) {
                int randomCord = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                wallCoordinates.add(new Coordinates(emptyCells.get(0).X() - 1 + randomCord % 3,
                        emptyCells.get(0).Y() - 1 + randomCord / 3));

                field.addFieldObjectWithCollision(wallCoordinates.get(i));
            }
            walls.add(new Wall(wallCoordinates));
            emptyCells.remove(0);
            emptyCells = field.getAllEmptyCells(1);
        }
    }

    /**
     * Check if snake can do a turn in the direction (snake can't turn 180°).
     *
     * @param snake     Snakes that turns
     * @param direction Direction to turn
     * @return true if snake can do a turn in the direction
     */
    public boolean isPossibleTurn(Snake snake, Direction direction) {
        return snake.isPossibleTurn(direction);
    }

    public Coordinates resize(int width, int height) {
        field.resize(width, height);
        for (Coordinates coordinates : userSnake.getCoordinates()) {
            if (coordinates.X() >= width - 1 || coordinates.Y() >= height - 1) {
                return coordinates;
            }
        }
        botSnakes.removeIf(botSnake -> botSnake.isOutOfBounds(width, height));
        allSnakes.removeIf(snake -> snake.isOutOfBounds(width, height));
        walls.removeIf(wall -> wall.isOutOfBounds(width, height));
        apples.removeIf(apple -> apple.isOutOfBounds(width, height));
        return null;
    }
}
