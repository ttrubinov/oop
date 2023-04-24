package ru.nsu.fit.trubinov.Model;

import ru.nsu.fit.trubinov.Model.Field.Coordinates;
import ru.nsu.fit.trubinov.Model.Field.Direction;
import ru.nsu.fit.trubinov.Model.Field.Grid;
import ru.nsu.fit.trubinov.Model.FieldObjects.Apple;
import ru.nsu.fit.trubinov.Model.FieldObjects.Snake;
import ru.nsu.fit.trubinov.Model.FieldObjects.Wall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// TODO: Optimization, especially wall spawning; Bot snakes movement
public class Model {
    private final Snake userSnake;
    private final List<Snake> botSnakes;
    private final List<Snake> allSnakes;
    private final List<Wall> walls;
    private final List<Apple> apples;
    private Grid grid;

    public Model(int width, int height) {
        grid = new Grid(width, height);
        userSnake = new Snake(new Coordinates(width / 2, height / 2));
        botSnakes = new ArrayList<>();
        allSnakes = new ArrayList<>();
        allSnakes.add(userSnake);
        allSnakes.addAll(botSnakes);
        walls = new ArrayList<>();
        apples = new ArrayList<>();
    }

    public static Coordinates intersectsWithGrid(Snake snake, Grid grid) {
        if (snake.getHead().getX() < 1 || snake.getHead().getX() >= grid.width() - 1 ||
                snake.getHead().getY() < 1 || snake.getHead().getY() >= grid.height() - 1) {
            return snake.getHead();
        }
        return null;
    }

    public static Coordinates intersectsWithWall(Snake snake, List<Wall> walls) {
        for (Wall wall : walls) {
            Coordinates intersection;
            if ((intersection = wall.intersects(snake)) != null) {
                return intersection;
            }
        }
        return null;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Snake getUserSnake() {
        return userSnake;
    }

    public List<Snake> getAllSnakes() {
        return allSnakes;
    }

    public List<Apple> getApples() {
        return apples;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Coordinates makeMove() {
        allSnakes.forEach(this::collectApples);
        allSnakes.forEach(Snake::move);
        Coordinates intersection;
        if ((intersection = deadlyCollision(userSnake)) != null) {
//            System.out.println("Itself:" + (userSnake.intersectsItself() != null));
//            System.out.println("Grid:" + (intersectsWithGrid(userSnake, grid) != null));
//            System.out.println("Wall:" + (intersectsWithWall(userSnake, walls) != null));
            return intersection;
        }
        allSnakes.removeIf(snake -> deadlyCollision(snake) != null);
        snakesCollision();
        allSnakes.removeIf(snake -> snake.length <= 0);
        getNewBotsDirection();
        spawnWalls();
        spawnApples();
        return null;
    }

    private void snakesCollision() {
        for (int i = 0; i < allSnakes.size() - 1; i++) {
            for (int j = i + 1; j < allSnakes.size(); j++) {
                Integer intersection = allSnakes.get(i).intersects(allSnakes.get(j));
                if (intersection != null) {
                    allSnakes.get(i).length = allSnakes.get(i).length + intersection;
                    allSnakes.get(j).length = allSnakes.get(j).length - intersection;
                }
            }
        }
    }

    private void collectApples(Snake snake) {
        apples.stream().filter(apple ->
                snake.getHead().add(snake.getDirection().getShiftByDirection())
                        .equals(apple.coordinates())).findFirst().ifPresent(apple -> {
            apples.remove(apple);
//            field.addEmptyCell(apple.coordinates());
            snake.length++;
        });
    }

    private void getNewBotsDirection() {
        for (Snake snake : botSnakes) {
            if (deadlyCollision(snake) == null) {
                continue;
            }
            List<Direction> randomDirections = Direction.getAllDirectionsRandomly();
            Snake newSnake = snake.possibleMove(snake.getDirection());
            while (randomDirections.size() > 0 && deadlyCollision(newSnake) != null) {
                newSnake.setDirection(randomDirections.get(0));
                randomDirections.remove(0);
                newSnake = snake.possibleMove(snake.getDirection());
            }
            snake.setDirection(newSnake.getDirection());
        }
    }

    private Coordinates deadlyCollision(Snake snake) {
        Coordinates intersection = snake.intersectsItself();
        if (intersection != null) {
            return intersection;
        }
        intersection = intersectsWithGrid(snake, grid);
        if (intersection != null) {
            return intersection;
        }
        intersection = intersectsWithWall(snake, walls);
        return intersection;
    }

    private void spawnApples() {
        if ((double) apples.size() >= Math.sqrt(grid.size()) / 5) {
            return;
        }
        List<Coordinates> emptyCells = getAllEmptyCells();
        Collections.shuffle(emptyCells);
        while ((double) apples.size() < Math.sqrt(grid.size()) / 5 && emptyCells.size() > 0) {
            apples.add(new Apple(emptyCells.get(0)));
            emptyCells.remove(0);
        }
    }

    private void spawnWalls() {
        if ((double) walls.size() >= Math.sqrt(grid.size())) {
            return;
        }
        List<Coordinates> emptyCells = getAllEmptyCells();
        Collections.shuffle(emptyCells);
        while ((double) walls.size() < Math.sqrt(grid.size()) && emptyCells.size() > 0) {
            int randomWallSize = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(grid.size()) / 8);
            List<Coordinates> wallCoordinates = new ArrayList<>();
            wallCoordinates.add(emptyCells.get(0));
            emptyCells.remove(0);
            for (int i = 0; i < randomWallSize - 1; i++) {
                ArrayList<Direction> randomDirections = new ArrayList<>(Direction.getAllDirectionsRandomly());
                boolean flag = true;
                for (Direction direction : randomDirections) {
                    if (!flag) {
                        break;
                    }
                    for (Coordinates cell : emptyCells) {
                        if (cell.equals(wallCoordinates.get(0).add(direction.getShiftByDirection()))) {
                            wallCoordinates.add(wallCoordinates.get(0).add(direction.getShiftByDirection()));
                            emptyCells.remove(cell);
                            flag = false;
                            break;
                        }
                    }
                }
            }
            walls.add(new Wall(wallCoordinates));
        }
    }

    public boolean isPossibleTurn(Direction newDirection) {
        return userSnake.getCoordinates().size() <= 1 ||
                !userSnake.getHead().add(newDirection.getShiftByDirection()).
                        equals(userSnake.getCoordinates().get(userSnake.getCoordinates().size() - 2));
    }

    private List<Coordinates> getAllEmptyCells() {
        List<Coordinates> allCells = grid.arrayOfCells();
        List<Coordinates> filledCells = new ArrayList<>();
        walls.forEach(wall -> filledCells.addAll(wall.coordinates()));
        apples.forEach(apple -> filledCells.add(apple.coordinates()));
        allSnakes.forEach(snake -> filledCells.addAll(snake.getCoordinates()));
        for (int i = 0; i < allCells.size(); i++) {
            for (Coordinates coordinates : filledCells) {
                if (allCells.get(i).equals(coordinates)) {
                    allCells.remove(i);
                    break;
                }
            }
        }
        return allCells;
    }
}
