package ru.nsu.fit.trubinov.Presenter.ConsolePresenter;

import com.googlecode.lanterna.TerminalSize;
import ru.nsu.fit.trubinov.Model.Field.Coordinates;
import ru.nsu.fit.trubinov.Model.Field.Direction;
import ru.nsu.fit.trubinov.Model.Field.Grid;
import ru.nsu.fit.trubinov.Model.Model;
import ru.nsu.fit.trubinov.View.ConsoleViewer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// TODO: game speed and resolution settings
public class ConsolePresenter {
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
                draw();
            }
        }, 0, gameSpeed, TimeUnit.MILLISECONDS);
    }

    private static void draw() {
        consoleViewer.doResizeIfNecessary();
        TerminalSize terminalSize = consoleViewer.getResizedTerminal();
        model.setGrid(new Grid(terminalSize.getColumns(), terminalSize.getRows()));
        consoleViewer.drawField();
        consoleViewer.drawApples(model.getApples());
        consoleViewer.drawWalls(model.getWalls());
        consoleViewer.drawGrid();
        consoleViewer.drawSnakes(model.getAllSnakes());
        consoleViewer.refresh();
    }

    private static void pollInput() {
        Direction newDirection = Direction.getDirectionByKey(consoleViewer.pollInput());
        if (newDirection != null && model.isPossibleTurn(newDirection)) {
            model.getUserSnake().setDirection(newDirection);
        }
    }
}
