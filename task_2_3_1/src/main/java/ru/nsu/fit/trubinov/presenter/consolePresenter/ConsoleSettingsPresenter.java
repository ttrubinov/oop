package ru.nsu.fit.trubinov.presenter.consolePresenter;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.TerminalResizeListener;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.utils.Coordinates;

import static ru.nsu.fit.trubinov.presenter.consolePresenter.ConsolePresenter.*;

public class ConsoleSettingsPresenter {
    private static int currentLine;

    private static boolean readInput() {
        KeyStroke input = consoleViewer.readInput();
        if (input == null) return true;
        if (input.getKeyType() == KeyType.Character) {
            if (input.getCharacter() == 'q') {
                exit();
                return false;
            }
        } else if (input.getKeyType() == KeyType.Escape) {
            return false;
        } else if (input.getKeyType() == KeyType.ArrowLeft) {
            if (currentLine == 0 && gameSpeed > 100) {
                gameSpeed -= 100;
            } else if (currentLine == 1 && difficultyLevel > 1) {
                difficultyLevel--;
                model = new Model(width, height, difficultyLevel);
            }
        } else if (input.getKeyType() == KeyType.ArrowRight) {
            if (currentLine == 0 && gameSpeed < 1000) {
                gameSpeed += 100;
            } else if (currentLine == 1 && difficultyLevel < 10) {
                difficultyLevel++;
                model = new Model(width, height, difficultyLevel);
            }
        } else if (input.getKeyType() == KeyType.ArrowDown && currentLine == 0) {
            currentLine++;
        } else if (input.getKeyType() == KeyType.ArrowUp && currentLine == 1) {
            currentLine--;
        }
        return true;
    }

    public static void settings() {
        TerminalResizeListener resizeListener = (terminal, newSize)
                -> ConsoleSettingsPresenter.doResizeIfNecessary();
        consoleViewer.terminal.addResizeListener(resizeListener);
        do {
            draw();
        } while (readInput());
        consoleViewer.terminal.removeResizeListener(resizeListener);
    }

    public static void doResizeIfNecessary() {
        consoleViewer.doResizeIfNecessary();
        TerminalSize terminalSize = consoleViewer.getResizedTerminal();
        width = terminalSize.getColumns();
        height = terminalSize.getRows();
        if (width != model.getMaxCoordinates().X() || height != model.getMaxCoordinates().Y()) {
            model.resize(width, terminalSize.getRows());
        }
        draw();
    }

    protected static void draw() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                consoleViewer.drawPixel(new Coordinates(i, j), ' ',
                        TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
            }
        }
        if (currentLine == 0) {
            drawGameSpeedSettings();
        } else {
            drawDifficultyLevelSettings();
        }
        consoleViewer.refresh();
    }

    protected static void drawGameSpeedSettings() {
        consoleViewer.drawTextMessage("Game speed",
                new Coordinates(width / 2 - "Game speed".length() / 2, height / 2 - 2),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage("< " + gameSpeed + " >",
                new Coordinates(width / 2 - String.valueOf(gameSpeed).length() / 2 - 2, height / 2 - 1),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage("Difficulty level",
                new Coordinates(width / 2 - "Difficulty level".length() / 2, height / 2 + 1),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage(String.valueOf(difficultyLevel),
                new Coordinates(width / 2 - String.valueOf(difficultyLevel).length() / 2, height / 2 + 2),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
    }

    protected static void drawDifficultyLevelSettings() {
        consoleViewer.drawTextMessage("Game speed",
                new Coordinates(width / 2 - "Game speed".length() / 2, height / 2 - 2),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage(String.valueOf(gameSpeed),
                new Coordinates(width / 2 - String.valueOf(gameSpeed).length() / 2, height / 2 - 1),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage("Difficulty level",
                new Coordinates(width / 2 - "Difficulty level".length() / 2, height / 2 + 1),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        consoleViewer.drawTextMessage("< " + difficultyLevel + " >",
                new Coordinates(width / 2 - String.valueOf(difficultyLevel).length() / 2 - 2, height / 2 + 2),
                TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
    }
}
