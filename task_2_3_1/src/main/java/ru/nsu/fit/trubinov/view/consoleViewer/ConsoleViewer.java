package ru.nsu.fit.trubinov.view.consoleViewer;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import ru.nsu.fit.trubinov.utils.Coordinates;

import java.io.IOException;
import java.util.List;

public class ConsoleViewer {
    private final Screen screen;
    public Terminal terminal;

    public ConsoleViewer() {
        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(70, 20)).createTerminal();
//            terminal.addResizeListener(new TerminalResizeListener() {
//                @Override
//                public void onResized(Terminal terminal, TerminalSize newSize) {
//                    screen.doResizeIfNecessary();
//                }
//            });
            screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConsoleViewer(int width, int height) {
        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(width, height)).createTerminal();
            screen = new TerminalScreen(terminal);
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

    public void drawPixel(Coordinates coordinate, TextCharacter character,
                          TextColor foregroundColor, TextColor backgroundColor) {
        screen.setCharacter(coordinate.X(), coordinate.Y(), character
                .withForegroundColor(foregroundColor)
                .withBackgroundColor(backgroundColor));
    }

    public void drawByCoordinates(List<Coordinates> coordinates, TextCharacter character,
                                  TextColor foregroundColor, TextColor backgroundColor) {
        for (Coordinates coordinate : coordinates) {
            screen.setCharacter(coordinate.X(), coordinate.Y(), character
                    .withForegroundColor(foregroundColor)
                    .withBackgroundColor(backgroundColor));
        }
    }

    public void doResizeIfNecessary() {
        screen.doResizeIfNecessary();
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

    public KeyStroke readInput() {
        try {
            return screen.readInput();
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

    public void drawIntersection(Coordinates coordinates, TextColor backgroundColor) {
        screen.setCharacter(coordinates.X(), coordinates.Y(),
                TextCharacter.DEFAULT_CHARACTER
                        .withCharacter('X')
                        .withForegroundColor(TextColor.ANSI.RED_BRIGHT)
                        .withBackgroundColor(backgroundColor));
    }

    public void drawTextMessage(String s, Coordinates coordinates, TextColor foregroundColor, TextColor backgroundColor) {
        for (int i = 0; i < s.length(); i++) {
            screen.setCharacter(coordinates.X() + i, coordinates.Y(),
                    TextCharacter.DEFAULT_CHARACTER
                            .withCharacter(s.charAt(i))
                            .withForegroundColor(foregroundColor)
                            .withBackgroundColor(backgroundColor));
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
            System.out.println("AAAAAAAAAAAAA");
            throw new RuntimeException(e);
        }
    }
}
