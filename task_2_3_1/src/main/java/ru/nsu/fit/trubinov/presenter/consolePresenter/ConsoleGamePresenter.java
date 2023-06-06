package ru.nsu.fit.trubinov.presenter.consolePresenter;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.fieldObjects.Apple;
import ru.nsu.fit.trubinov.model.fieldObjects.Wall;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.BotSnake;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.utils.Direction;
import ru.nsu.fit.trubinov.view.consoleViewer.ConsoleViewer;
import ru.nsu.fit.trubinov.view.consoleViewer.Symbols;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ru.nsu.fit.trubinov.presenter.consolePresenter.ConsolePresenter.*;
import static ru.nsu.fit.trubinov.utils.FieldObject.*;

/**
 * Presenter of the game screen in console.
 */
public class ConsoleGamePresenter {
    private static final long waitTimeBeforeGameStop = 1000;
    private static final TextColor fieldColor = new TextColor.RGB(0, 150, 0);
    private static final Map<Character, Direction> directionsKeyMap = Map.of(
            'W', Direction.UP, 'w', Direction.UP,
            'A', Direction.LEFT, 'a', Direction.LEFT,
            'S', Direction.DOWN, 's', Direction.DOWN,
            'D', Direction.RIGHT, 'd', Direction.RIGHT
    );

    /**
     * Starting a game.
     */
    protected void startGame() {
        if (!pollInput()) {
            executor.shutdown();
            return;
        }
        draw();
        Coordinates intersection, outOfField;
        outOfField = doResizeIfNecessary();
        intersection = model.makeMove();
        draw();
        if (outOfField != null || intersection != null) {
            consoleViewer.drawIntersection(Objects.requireNonNullElse(intersection, outOfField), fieldColor);
            consoleViewer.drawDieMessage();
            consoleViewer.refresh();
            executor.shutdown();
            try {
                Thread.sleep(waitTimeBeforeGameStop);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            model = new Model(width, height, difficultyLevel);
        }
    }

    private boolean pollInput() {
        KeyStroke input = consoleViewer.pollInput();
        if (input == null) return true;
        if (input.getKeyType() == KeyType.Character) {
            if (input.getCharacter() == 'q') {
                exit();
            }
        }
        if (input.getKeyType() == KeyType.Escape) {
            return false;
        }
        Direction newDirection = directionsKeyMap.get(input.getCharacter());
        if (newDirection != null && model.isPossibleTurn(model.getUserSnake(), newDirection)) {
            model.getUserSnake().setDirection(newDirection);
        }
        return true;
    }

    /**
     * Drawing everything from field.
     */
    protected void draw() {
        drawField();
        drawApples(model.getApples());
        drawWalls(model.getWalls());
        drawSnakes(model.getUserSnake(), model.getBotSnakes());
        consoleViewer.refresh();
    }

    private void drawWalls(List<Wall> walls) {
        walls.forEach(wall -> consoleViewer.drawByCoordinates(wall.getCoordinates(),
                Symbols.symbolMap.get(WALL), new TextColor.RGB(0, 0, 0), fieldColor));

    }

    private void drawField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    consoleViewer.drawPixel(new Coordinates(i, j), Symbols.symbolMap.get(WALL),
                            new TextColor.RGB(0, 0, 0), fieldColor);
                } else {
                    consoleViewer.drawPixel(new Coordinates(i, j), Symbols.symbolMap.get(NOTHING), fieldColor, fieldColor);
                }
            }
        }
    }

    private void drawApples(List<Apple> apples) {
        apples.forEach(apple -> consoleViewer.drawByCoordinates(apple.getCoordinates(),
                Symbols.symbolMap.get(APPLE), new TextColor.RGB(200, 0, 0), fieldColor));
    }

    private void drawSnakes(Snake userSnake, List<BotSnake> botSnakes) {
        consoleViewer.drawPixel(userSnake.getHead(), Symbols.headSymbolMap.get(userSnake.getDirection()),
                new TextColor.RGB(0, 255, 0), fieldColor);
        for (int i = 0; i < userSnake.getCoordinates().size() - 1; i++) {
            int r = ConsoleViewer.getGradient(userSnake.getCoordinates(), i);
            int g = 255;
            int b = ConsoleViewer.getGradient(userSnake.getCoordinates(), i);
            consoleViewer.drawPixel(userSnake.getCoordinates().get(i), Symbols.symbolMap.get(SNAKE),
                    new TextColor.RGB(r, g, b), fieldColor);
        }
        for (Snake botSnake : botSnakes) {
            consoleViewer.drawPixel(botSnake.getHead(), Symbols.headSymbolMap.get(botSnake.getDirection()),
                    new TextColor.RGB(0, 0, 0), fieldColor);
            for (int i = 0; i < botSnake.getCoordinates().size() - 1; i++) {
                int r = 50;
                int g = ConsoleViewer.getGradient(botSnake.getCoordinates(), i);
                int b = ConsoleViewer.getGradient(botSnake.getCoordinates(), i);
                consoleViewer.drawPixel(botSnake.getCoordinates().get(i), Symbols.symbolMap.get(SNAKE),
                        new TextColor.RGB(r, g, b), fieldColor);
            }
        }
    }

    /**
     * Resizing.
     *
     * @return coordinates of user snake's death intersection if such happened
     */
    protected Coordinates doResizeIfNecessary() {
        consoleViewer.doResizeIfNecessary();
        TerminalSize terminalSize = consoleViewer.getResizedTerminal();
        width = terminalSize.getColumns();
        height = terminalSize.getRows();
        if (terminalSize.getColumns() != model.getMaxCoordinates().X() || terminalSize.getRows() != model.getMaxCoordinates().Y()) {
            return model.resize(terminalSize.getColumns(), terminalSize.getRows());
        }
        return null;
    }
}
