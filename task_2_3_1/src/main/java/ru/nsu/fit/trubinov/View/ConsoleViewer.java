package ru.nsu.fit.trubinov.View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import ru.nsu.fit.trubinov.Model.Field.Coordinates;
import ru.nsu.fit.trubinov.Model.FieldObjects.Apple;
import ru.nsu.fit.trubinov.Model.FieldObjects.Snake;
import ru.nsu.fit.trubinov.Model.FieldObjects.Wall;

import java.io.IOException;
import java.util.List;

// TODO: Buttons, settings
@SuppressWarnings("unused")
public class ConsoleViewer {
    private final Screen screen;
    private int width;
    private int height;

    public ConsoleViewer() {
        try {
            Terminal terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(70, 20)).createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConsoleViewer(int width, int height) {
        try {
            Terminal terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(width, height)).createTerminal();
            screen = new TerminalScreen(terminal);
            this.width = width;
            this.height = height;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                screen.setCharacter(i, j, TextCharacter.DEFAULT_CHARACTER
                        .withBackgroundColor(TextColor.ANSI.GREEN));
            }
        }
    }

    public void doResizeIfNecessary() {
        screen.doResizeIfNecessary();
        width = screen.getTerminalSize().getColumns();
        height = screen.getTerminalSize().getRows();
    }

    public TerminalSize getResizedTerminal() {
        return screen.getTerminalSize();
    }

    public KeyStroke pollInput() {
        try {
            return screen.pollInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh() {
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawSnakes(List<Snake> snakes) {
        snakes.forEach(snake -> {
            screen.setCharacter(snake.getHead().getX(), snake.getHead().getY(),
                    TextCharacter.DEFAULT_CHARACTER
                            .withCharacter(snake.getDirection().headCharacter)
                            .withForegroundColor(TextColor.ANSI.GREEN_BRIGHT)
                            .withBackgroundColor(TextColor.ANSI.GREEN));
            for (int i = 0; i < snake.getBodyCoordinates().size() - 1; i++) {
                screen.setCharacter(snake.getBodyCoordinates().get(i).getX(), snake.getBodyCoordinates().get(i).getY(),
                        TextCharacter.DEFAULT_CHARACTER
                                .withCharacter('@')
                                .withForegroundColor(TextColor.ANSI.GREEN_BRIGHT)
                                .withBackgroundColor(TextColor.ANSI.GREEN));
            }
        });
    }

    public void drawApples(List<Apple> apples) {
        apples.forEach(apple -> screen.setCharacter(apple.coordinates().getX(), apple.coordinates().getY(),
                TextCharacter.DEFAULT_CHARACTER
                        .withCharacter('O')
                        .withForegroundColor(TextColor.ANSI.RED)
                        .withBackgroundColor(TextColor.ANSI.GREEN)));
    }

    public void drawWalls(List<Wall> walls) {
        walls.forEach(wall -> {
            for (Coordinates coordinates : wall.wallCoordinates()) {
                screen.setCharacter(coordinates.getX(), coordinates.getY(),
                        TextCharacter.DEFAULT_CHARACTER
                                .withCharacter('#')
                                .withForegroundColor(TextColor.ANSI.BLACK)
                                .withBackgroundColor(TextColor.ANSI.GREEN));
            }
        });
    }


    public void drawGrid() {
        int width = screen.getTerminalSize().getColumns();
        int height = screen.getTerminalSize().getRows();
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    screen.setCharacter(i, j, TextCharacter.DEFAULT_CHARACTER
                            .withCharacter('#')
                            .withForegroundColor(TextColor.ANSI.BLACK)
                            .withBackgroundColor(TextColor.ANSI.GREEN));
                }
            }
        }
    }

    public void drawIntersection(Coordinates coordinates) {
        screen.setCharacter(coordinates.getX(), coordinates.getY(),
                TextCharacter.DEFAULT_CHARACTER
                        .withCharacter('X')
                        .withForegroundColor(TextColor.ANSI.RED_BRIGHT)
                        .withBackgroundColor(TextColor.ANSI.GREEN));
    }

    public void drawTextMessage(String s) {
        for (int i = 0; i < s.length(); i++) {
            screen.setCharacter(i + 5, screen.getTerminalSize().getRows() / 5,
                    TextCharacter.DEFAULT_CHARACTER
                            .withCharacter(s.charAt(i))
                            .withForegroundColor(TextColor.ANSI.BLACK)
                            .withBackgroundColor(TextColor.ANSI.GREEN));
        }
    }

    public void drawDieMessage() {
        String s = "GAME OVER";
        for (int i = 0; i < s.length(); i++) {
            screen.setCharacter(screen.getTerminalSize().getColumns() / 2 - s.length() / 2 + i,
                    screen.getTerminalSize().getRows() / 2,
                    TextCharacter.DEFAULT_CHARACTER
                            .withCharacter(s.charAt(i))
                            .withForegroundColor(TextColor.ANSI.RED)
                            .withBackgroundColor(TextColor.ANSI.BLACK));
        }
    }


    public void exit() {
        try {
            screen.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
