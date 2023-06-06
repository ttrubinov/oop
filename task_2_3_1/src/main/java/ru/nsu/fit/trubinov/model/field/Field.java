package ru.nsu.fit.trubinov.model.field;

import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.utils.FieldObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.nsu.fit.trubinov.utils.FieldObject.*;

public class Field implements Cloneable {
    private int width;
    private int height;
    private FieldObject[][] field;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        field = new FieldObject[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    field[i][j] = WALL;
                } else {
                    field[i][j] = NOTHING;
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinates getWidthAndHeight() {
        return new Coordinates(width, height);
    }

    public int size() {
        return width * height;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                s.append(field[i][j]);
                s.append(' ');
            }
            if (j != height - 1) {
                s.append('\n');
            }
        }
        return String.valueOf(s);
    }

    public void resize(int width, int height) {
        Field newField = new Field(width, height);
        for (int i = 0; i < Math.min(this.width, width); i++) {
            for (int j = 0; j < Math.min(this.height, height); j++) {
                newField.field[i][j] = this.field[i][j];
                if (i == this.width - 1 && i != width - 1 && j > 0 ||
                        j == this.height - 1 && j != height - 1 && i > 0) { // delete old outer walls
                    newField.field[i][j] = NOTHING;
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == width - 1 || j == height - 1) {
                    newField.field[i][j] = WALL; // add new outer walls
                }
            }
        }
        this.width = width;
        this.height = height;
        this.field = newField.field;
    }

    /**
     * Add a wall to the field.
     *
     * @param coordinates Coordinates of a wall
     */
    public void addWall(Coordinates coordinates) {
        if (coordinates.X() > 0 && coordinates.Y() > 0 && coordinates.X() < width - 1 && coordinates.Y() < height - 1) {
            field[coordinates.X()][coordinates.Y()] = WALL;
        }
    }

    /**
     * Add a snake to the field.
     *
     * @param coordinates Coordinates of a snake
     */
    public void addSnake(Coordinates coordinates) {
        if (coordinates.X() > 0 && coordinates.Y() > 0 && coordinates.X() < width - 1 && coordinates.Y() < height - 1) {
            field[coordinates.X()][coordinates.Y()] = SNAKE;
        }
    }

    /**
     * Add a field object without collision (apple) to the field.
     *
     * @param coordinates Coordinates of a field object without collision
     */
    public void addNoCollisionFieldObject(Coordinates coordinates) {
        if (coordinates.X() > 0 && coordinates.Y() > 0 && coordinates.X() < width - 1 && coordinates.Y() < height - 1) {
            field[coordinates.X()][coordinates.Y()] = APPLE;
        }
    }

    /**
     * Add an empty cell to the field.
     *
     * @param coordinates Coordinates of an empty cell
     */
    public void addEmptyCell(Coordinates coordinates) {
        if (coordinates.X() > 0 && coordinates.Y() > 0 && coordinates.X() < width - 1 && coordinates.Y() < height - 1) {
            field[coordinates.X()][coordinates.Y()] = NOTHING;
        }
    }

    public Coordinates deathIntersectionCoordinates(Snake snake) {
        Coordinates coordinates = snake.getNextHeadPosition();
        try {
            if (field[coordinates.X()][coordinates.Y()] != APPLE &&
                    field[coordinates.X()][coordinates.Y()] != NOTHING &&
                    !coordinates.equals(snake.getCoordinates().get(0))) {
                return coordinates;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return coordinates;
        }
        return null;
    }

    public boolean isEmpty(Coordinates coordinates) {
        return field[coordinates.X()][coordinates.Y()] == NOTHING;
    }

    public boolean intersectionWithApple(Snake snake) {
        Coordinates coordinates = snake.getNextHeadPosition();
        return field[coordinates.X()][coordinates.Y()] == APPLE;
    }

    public boolean deathIntersection(Snake snake) {
        Coordinates coordinates = snake.getNextHeadPosition();
        return !coordinates.equals(snake.getCoordinates().get(0)) &&
                field[coordinates.X()][coordinates.Y()] != APPLE &&
                field[coordinates.X()][coordinates.Y()] != NOTHING;
    }

    public boolean isSnakeIntersection(Snake snake) {
        Coordinates coordinates = snake.getNextHeadPosition();
        return !coordinates.equals(snake.getCoordinates().get(0)) && field[coordinates.X()][coordinates.Y()] == SNAKE;
    }

    public boolean isWallIntersection(Snake snake) {
        Coordinates coordinates = snake.getNextHeadPosition();
        return !coordinates.equals(snake.getCoordinates().get(0)) && field[coordinates.X()][coordinates.Y()] == WALL;
    }

    /**
     * Get all empty cells on the field with empty space around this cell.
     *
     * @param emptySpace Empty space around every cell needed
     * @return List of coordinates of empty cells
     */
    public List<Coordinates> getAllEmptyCells(int emptySpace) {
        List<Coordinates> emptyCells = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                int cnt = 0;
                for (int k = -emptySpace; k <= emptySpace; k++) {
                    for (int l = -emptySpace; l <= emptySpace; l++) {
                        try {
                            if (field[i + k][j + l] == NOTHING) {
                                cnt++;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                }
                if (cnt == (2 * emptySpace + 1) * (2 * emptySpace + 1)) {
                    if (field[i][j] == NOTHING) {
                        emptyCells.add(new Coordinates(i, j));
                    }
                }
            }
        }
        Collections.shuffle(emptyCells);
        return emptyCells;
    }

    @Override
    public Field clone() {
        Field field = new Field(this.width, this.height);
        for (int i = 0; i < width; i++) {
            if (height >= 0) System.arraycopy(this.field[i], 0, field.field[i], 0, height);
        }
        return field;
    }
}
