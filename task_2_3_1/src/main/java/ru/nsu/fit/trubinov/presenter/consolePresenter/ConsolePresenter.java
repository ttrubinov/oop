package ru.nsu.fit.trubinov.presenter.consolePresenter;

import com.googlecode.lanterna.terminal.TerminalResizeListener;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.presenter.Presenter;
import ru.nsu.fit.trubinov.view.consoleViewer.ConsoleViewer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.nsu.fit.trubinov.presenter.consolePresenter.ConsoleSettingsPresenter.settings;


public class ConsolePresenter implements Presenter {
    protected static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    protected static int width = 40;
    protected static int height = 20;
    protected static final ConsoleViewer consoleViewer = new ConsoleViewer(width, height);
    protected static int difficultyLevel = 5;
    protected static int gameSpeed = 400;
    protected static boolean exitFlag = true;
    protected static Model model = new Model(width, height, difficultyLevel);

    public ConsolePresenter(int width, int height) {
        ConsolePresenter.width = width;
        ConsolePresenter.height = height;
    }

    public static void main(String[] args) throws InterruptedException {
        consoleViewer.start();
        while (exitFlag) {
            settings();
            if (!exitFlag) {
                break;
            }
            TerminalResizeListener resizeListener = (terminal, newSize) -> {
                ConsoleGamePresenter.doResizeIfNecessary();
                ConsoleGamePresenter.draw();
            };
            consoleViewer.terminal.addResizeListener(resizeListener);
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(ConsoleGamePresenter::game, 0, gameSpeed, TimeUnit.MILLISECONDS);
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            consoleViewer.terminal.removeResizeListener(resizeListener);
        }
    }

    protected static void exit() {
        executor.shutdown();
        consoleViewer.exit();
        exitFlag = false;
    }
}
