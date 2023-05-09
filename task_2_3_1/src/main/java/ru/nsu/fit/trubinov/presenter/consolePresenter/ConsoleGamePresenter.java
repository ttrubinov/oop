package ru.nsu.fit.trubinov.presenter.consolePresenter;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.fieldObjects.Apple;
import ru.nsu.fit.trubinov.model.fieldObjects.Wall;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.BotSnake;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.List;
import java.util.Objects;

import static ru.nsu.fit.trubinov.presenter.consolePresenter.ConsolePresenter.*;

public class ConsoleGamePresenter {
    private static final long waitTimeBeforeGameStop = 1000;
    private static final TextColor fieldColor = new TextColor.RGB(0, 150, 0);

    protected static void game() {
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
            System.out.println("Shutdown");
            try {
                Thread.sleep(waitTimeBeforeGameStop);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            model = new Model(width, height, difficultyLevel);
        }
    }

    private static boolean pollInput() {
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
        Direction newDirection = Direction.getDirectionByKey(input);
        if (newDirection != null && model.isPossibleTurn(model.getUserSnake(), newDirection)) {
            model.getUserSnake().setDirection(newDirection);
        }
        return true;
    }

    protected static void draw() {
        drawField();
        drawApples(model.getApples());
        drawWalls(model.getWalls());
        drawSnakes(model.getUserSnake(), model.getBotSnakes());
        consoleViewer.refresh();
    }

    private static void drawWalls(List<Wall> walls) {
        walls.forEach(wall -> consoleViewer.drawByCoordinates(wall.getCoordinates(),
                TextCharacter.DEFAULT_CHARACTER.withCharacter('#'), new TextColor.RGB(0, 0, 0), fieldColor));

    }

    private static void drawField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    consoleViewer.drawPixel(new Coordinates(i, j),
                            TextCharacter.DEFAULT_CHARACTER.withCharacter('#'),
                            new TextColor.RGB(0, 0, 0), fieldColor);
                } else {
                    consoleViewer.drawPixel(new Coordinates(i, j),
                            TextCharacter.DEFAULT_CHARACTER, fieldColor, fieldColor);
                }
            }
        }
    }

    private static void drawApples(List<Apple> apples) {
        apples.forEach(apple -> consoleViewer.drawByCoordinates(apple.getCoordinates(),
                TextCharacter.DEFAULT_CHARACTER.withCharacter('O'), new TextColor.RGB(200, 0, 0), fieldColor));
    }

    private static void drawSnakes(Snake userSnake, List<BotSnake> botSnakes) {
        consoleViewer.drawPixel(userSnake.getHead(), TextCharacter.DEFAULT_CHARACTER
                .withCharacter(userSnake.getDirection().headCharacter), new TextColor.RGB(0, 255, 0), fieldColor);
        for (int i = 0; i < userSnake.getCoordinates().size() - 1; i++) {
            int r = getGradient(userSnake.getCoordinates(), i);
            int g = 255;
            int b = getGradient(userSnake.getCoordinates(), i);
            consoleViewer.drawPixel(userSnake.getCoordinates().get(i),
                    TextCharacter.DEFAULT_CHARACTER.withCharacter('@'), new TextColor.RGB(r, g, b), fieldColor);
        }
        for (Snake botSnake : botSnakes) {
            consoleViewer.drawPixel(botSnake.getHead(),
                    TextCharacter.DEFAULT_CHARACTER.withCharacter(botSnake.getDirection().headCharacter),
                    new TextColor.RGB(0, 0, 0), fieldColor);
            for (int i = 0; i < botSnake.getCoordinates().size() - 1; i++) {
                int r = getGradient(botSnake.getCoordinates(), i);
                int g = getGradient(botSnake.getCoordinates(), i);
                int b = getGradient(botSnake.getCoordinates(), i);
                consoleViewer.drawPixel(botSnake.getCoordinates().get(i),
                        TextCharacter.DEFAULT_CHARACTER.withCharacter('@'),
                        new TextColor.RGB(r, g, b), fieldColor);
            }
        }
    }

    private static int getGradient(List<Coordinates> coordinates, int i) {
        return 255 / (coordinates.size()) * (coordinates.size() - i - 1);
    }

    protected static Coordinates doResizeIfNecessary() {
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
