package ru.nsu.fit.trubinov.presenter.consolePresenter;

import com.googlecode.lanterna.TerminalSize;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.presenter.Presenter;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.view.ConsoleViewer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// TODO: game speed and resolution settings
public class ConsolePresenter implements Presenter {
    private static final int gameSpeed = 400;
    private static int width = 40;
    private static int height = 20;
    public static final Model model = new Model(width, height);
    private static final ConsoleViewer consoleViewer = new ConsoleViewer(width, height);

    public ConsolePresenter(int width, int height) {
        ConsolePresenter.width = width;
        ConsolePresenter.height = height;
    }

    public static void main(String[] args) {
        consoleViewer.start();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            pollInput();
            Coordinates intersection;
            if ((intersection = model.makeMove()) != null) {
                doResizeIfNecessary();
                draw();
                consoleViewer.drawIntersection(intersection);
                consoleViewer.drawDieMessage();
                consoleViewer.refresh();
                executor.shutdown();
                System.out.println("Shutdown");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                consoleViewer.exit();
            } else {
                doResizeIfNecessary();
                draw();
            }
        }, 0, gameSpeed, TimeUnit.MILLISECONDS);
    }

    private static void draw() {
        consoleViewer.drawField();
        consoleViewer.drawApples(model.getApples());
        consoleViewer.drawWalls(model.getWalls());
        consoleViewer.drawGrid();
        consoleViewer.drawSnakes(model.getUserSnake(), model.getBotSnakes());
        consoleViewer.refresh();
    }

    private static void doResizeIfNecessary() {
        consoleViewer.doResizeIfNecessary();
        TerminalSize terminalSize = consoleViewer.getResizedTerminal();
        if (terminalSize.getColumns() != model.getMaxCoordinates().X() || terminalSize.getRows() != model.getMaxCoordinates().Y()) {
            model.resize(terminalSize.getColumns(), terminalSize.getRows());
        }
    }

    private static void pollInput() {
        Direction newDirection = Direction.getDirectionByKey(consoleViewer.pollInput());
        if (newDirection != null && model.isPossibleTurn(newDirection)) {
            model.getUserSnake().setDirection(newDirection);
        }
    }
}
