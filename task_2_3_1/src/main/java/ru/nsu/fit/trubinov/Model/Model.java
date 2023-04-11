package ru.nsu.fit.trubinov.Model;

import ru.nsu.fit.trubinov.Model.Field.Grid;
import ru.nsu.fit.trubinov.Model.FieldObjects.Apple;
import ru.nsu.fit.trubinov.Model.FieldObjects.Snake;
import ru.nsu.fit.trubinov.Model.FieldObjects.Wall;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Model {
    private final Grid grid;
    private final Snake userSnake;
    private final ArrayList<Snake> allSnakes;
    private final ArrayList<Wall> walls;
    private final ArrayList<Apple> apples;
    private int speed;

    public Model(Grid grid, Snake userSnake, ArrayList<Snake> botSnakes,
                 ArrayList<Wall> walls, ArrayList<Apple> apples, int speed) {
        this.grid = grid;
        this.userSnake = userSnake;
        this.allSnakes = new ArrayList<>();
        this.allSnakes.add(userSnake);
        this.allSnakes.addAll(botSnakes);
        this.walls = walls;
        this.apples = apples;
        this.speed = speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            allSnakes.forEach(Snake::move);
            if (userSnake.intersectsItself() || intersectsWithWall(userSnake)) {
                executor.shutdown();
            }
            allSnakes.removeIf(Snake::intersectsItself);
            allSnakes.removeIf(this::intersectsWithWall);
            allSnakes.removeIf(this::intersectsWithGrid);
            for (int i = 0; i < allSnakes.size() - 1; i++) {
                for (int j = i; j < allSnakes.size(); j++) {
                    Integer intersection = allSnakes.get(i).intersects(allSnakes.get(j));
                    if (intersection != null) {
                        allSnakes.get(i).setLength(allSnakes.get(i).length + intersection);
                        allSnakes.get(j).setLength(allSnakes.get(j).length - intersection);
                    }
                }
            }
            allSnakes.forEach(this::collectApples);
        }, 0, 10 - (speed % 10), TimeUnit.MILLISECONDS);
    }

    private boolean intersectsWithGrid(Snake snake) {
        return snake.getHead().getX() < grid.width() && snake.getHead().getY() < grid.height();
    }

    private boolean intersectsWithWall(Snake snake) {
        return walls.stream().anyMatch(wall -> wall.intersects(snake));
    }

    private void collectApples(Snake snake) {
        if (apples.stream().anyMatch(apple -> snake.getHead().equals(apple.coordinates()))) {
            snake.length++;
        }
    }
}
