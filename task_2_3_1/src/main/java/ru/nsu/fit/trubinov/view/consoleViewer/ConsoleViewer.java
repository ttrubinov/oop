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

/**
 * Drawing in console.
 */
public class ConsoleViewer {
    private final Screen screen;
    public Terminal terminal;

    public ConsoleViewer(int width, int height) {
        try {
            terminal = new DefaultTerminalFactory()
                    .setInitialTerminalSize(new TerminalSize(width, height)).createTerminal();
            screen = new TerminalScreen(terminal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get color gradient.
     *
     * @param coordinates list of coordinates to get gradient on
     * @param index       index of current cell
     * @return gradient value
     */
    public static int getGradient(List<Coordinates> coordinates, int index) {
        return 255 / (coordinates.size()) * (coordinates.size() - index - 1);
    }

    /**
     * Start the screen.
     */
    public void start() {
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draw 1 pixel.
     *
     * @param coordinate      coordinates of the pixel.
     * @param character       character to draw on the pixel.
     * @param foregroundColor foreground color of the pixel.
     * @param backgroundColor backgroundColor color of the pixel.
     */
    public void drawPixel(Coordinates coordinate, char character,
                          TextColor foregroundColor, TextColor backgroundColor) {
        screen.setCharacter(coordinate.X(), coordinate.Y(), TextCharacter.DEFAULT_CHARACTER
                .withCharacter(character)
                .withForegroundColor(foregroundColor)
                .withBackgroundColor(backgroundColor));
    }

    /**
     * Draw many pixels by the list of coordinates.
     *
     * @param coordinates     coordinates of the pixels.
     * @param character       character to draw on the pixels.
     * @param foregroundColor foreground color of the pixels.
     * @param backgroundColor backgroundColor color of the pixels.
     */
    public void drawByCoordinates(List<Coordinates> coordinates, char character,
                                  TextColor foregroundColor, TextColor backgroundColor) {
        for (Coordinates coordinate : coordinates) {
            screen.setCharacter(coordinate.X(), coordinate.Y(), TextCharacter.DEFAULT_CHARACTER.withCharacter(character)
                    .withForegroundColor(foregroundColor)
                    .withBackgroundColor(backgroundColor));
        }
    }

    /**
     * Resizing.
     */
    public void doResizeIfNecessary() {
        screen.doResizeIfNecessary();
    }

    public TerminalSize getResizedTerminal() {
        return screen.getTerminalSize();
    }

    /**
     * Polling user input.
     *
     * @return key that user pressed
     */
    public KeyStroke pollInput() {
        try {
            return screen.pollInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reading user input.
     *
     * @return key that user pressed
     */
    public KeyStroke readInput() {
        try {
            return screen.readInput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Refreshing the screen.
     */
    public void refresh() {
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Draw intersection on the screen.
     *
     * @param coordinates     coordinates of the intersection
     * @param backgroundColor backgroundColor to draw
     */
    public void drawIntersection(Coordinates coordinates, TextColor backgroundColor) {
        screen.setCharacter(coordinates.X(), coordinates.Y(),
                TextCharacter.DEFAULT_CHARACTER
                        .withCharacter('X')
                        .withForegroundColor(TextColor.ANSI.RED_BRIGHT)
                        .withBackgroundColor(backgroundColor));
    }

    /**
     * Draw text message on the screen.
     *
     * @param coordinates     coordinates of the text message
     * @param foregroundColor foregroundColor to draw
     * @param backgroundColor backgroundColor to draw
     */
    public void drawTextMessage(String s, Coordinates coordinates, TextColor foregroundColor, TextColor backgroundColor) {
        for (int i = 0; i < s.length(); i++) {
            screen.setCharacter(coordinates.X() + i, coordinates.Y(),
                    TextCharacter.DEFAULT_CHARACTER
                            .withCharacter(s.charAt(i))
                            .withForegroundColor(foregroundColor)
                            .withBackgroundColor(backgroundColor));
        }
    }

    /**
     * Draw die message on the screen.
     */
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

    /**
     * Exit from the screen.
     */
    public void exit() {
        try {
            screen.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
