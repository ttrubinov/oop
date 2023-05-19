package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.fieldObjects.Apple;
import ru.nsu.fit.trubinov.model.fieldObjects.Wall;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.BotSnake;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.presenter.Presenter;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.utils.Direction;
import ru.nsu.fit.trubinov.view.javaFXViewer.JavaFXViewer;
import ru.nsu.fit.trubinov.view.javaFXViewer.Textures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFXPresenter implements Presenter {
    private final Stage stage;
    private final JavaFXViewer viewer;
    private final Map<KeyCode, Direction> keyMap = Map.of(
            KeyCode.W, Direction.UP,
            KeyCode.UP, Direction.UP,
            KeyCode.A, Direction.LEFT,
            KeyCode.LEFT, Direction.LEFT,
            KeyCode.S, Direction.DOWN,
            KeyCode.DOWN, Direction.DOWN,
            KeyCode.D, Direction.RIGHT,
            KeyCode.RIGHT, Direction.RIGHT
    );
    protected int width;
    protected int height;
    private Model model;
    private int difficultyLevel = 10;
    private Direction lastDirection;
    private Map<Snake, Color> snakeColorMap;

    public JavaFXPresenter(Stage stage) {
        this.stage = stage;
        width = 512;
        height = 512;
        model = new Model(width / 64, height / 64, difficultyLevel);
        viewer = new JavaFXViewer();
    }

    public void init() {
        viewer.getScene().setOnKeyPressed(this::onKeyPressed);
        model = new Model(width / 64, height / 64, difficultyLevel);
        snakeColorMap = new HashMap<>();
        snakeColorMap.put(model.getUserSnake(), Color.GREEN);
        model.getBotSnakes().forEach(botSnake -> snakeColorMap.put(botSnake, SnakeColor.getRandomSnakeColor().color));
        setResizeListener();
        setBotSpawnListener();
        draw();
    }

    protected void start() {
        viewer.initScene(stage, width, height);
        init();

//        EventHandler<ActionEvent> eventHandler = null;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
                new EventHandler<>() {

                    public void handle(ActionEvent event) {
                        changeDirection();
                        Coordinates intersection = model.makeMove();
                        draw();
                        if (intersection != null) {
                            stage.close();
                        }
                    }
                }));
//        eventHandler.handle(eventHandler);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setBotSpawnListener() {
        model.setSpawnBotsListener(this::addNewBot);
    }

    public void addNewBot(BotSnake botSnake) {
        snakeColorMap.put(botSnake, SnakeColor.getRandomSnakeColor().color);
    }

    private void draw() {
        drawField();
        drawSnake(model.getUserSnake());
        model.getBotSnakes().forEach(this::drawSnake);
        drawWalls(model.getWalls());
        drawApples(model.getApples());
        drawGrid();
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        Direction direction = getDirectionByKeyEvent(keyEvent);
        if (direction != null && model.getUserSnake().isPossibleTurn(direction)) {
            lastDirection = direction;
        }
    }

    private Direction getDirectionByKeyEvent(KeyEvent keyEvent) {
        return keyMap.get(keyEvent.getCode());
    }

    private void changeDirection() {
        if (lastDirection != null) {
            model.getUserSnake().setDirection(lastDirection);
        }
    }

    private void drawField() {
        for (int i = 0; i < width; i += 64) {
            for (int j = 0; j < height; j += 64) {
                if (i >= width - width % 64 || j >= height - height % 64) {
                    viewer.drawDefaultImage(Textures.BEDROCK.image, new Coordinates(i, j));
                } else {
                    if ((i + j) % (2 * 64) == 0) {
                        viewer.drawDefaultImage(Textures.FIELD1.image, new Coordinates(i, j));
                    } else {
                        viewer.drawDefaultImage(Textures.FIELD2.image, new Coordinates(i, j));
                    }
                }
            }
        }
    }

    private void drawSnake(Snake snake) {
        viewer.drawImage(Textures.HEAD.image, snake.getDirection().angle,
                snakeColorMap.get(snake), snake.getHead().multiply(64));
        if (snake.getCoordinates().size() > 1) {
            viewer.drawImage(Textures.TAIL_END.image,
                    snake.getSnakeCeilDirection(snake.getCoordinates().get(1)).angle,
                    snakeColorMap.get(snake), snake.getCoordinates().get(0).multiply(64));
        }
        for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
            Coordinates coordinates = snake.getCoordinates().get(i);
            int fstAngle;
            int sndAngle;
            fstAngle = snake.getSnakeCeilDirection(coordinates).angle;
            sndAngle = snake.getSnakeCeilDirection(snake.getCoordinates().get(i + 1)).angle;

            if (fstAngle == sndAngle) {
                viewer.drawImage(Textures.TAIL.image, fstAngle, snakeColorMap.get(snake), coordinates.multiply(64));
                continue;
            }
            if ((fstAngle == 180 && sndAngle == 270) || (fstAngle == 90 && sndAngle == 0)) {
                viewer.drawImage(Textures.ANGLED_TAIL.image, 0,
                        snakeColorMap.get(snake), coordinates.multiply(64));
            } else if ((fstAngle == 270 && sndAngle == 0) || (fstAngle == 180 && sndAngle == 90)) {
                viewer.drawImage(Textures.ANGLED_TAIL.image, 90, snakeColorMap.get(snake),
                        coordinates.multiply(64));
            } else if ((fstAngle == 0 && sndAngle == 90) || (fstAngle == 270 && sndAngle == 180)) {
                viewer.drawImage(Textures.ANGLED_TAIL.image, 180, snakeColorMap.get(snake),
                        coordinates.multiply(64));
            } else if ((fstAngle == 90 && sndAngle == 180) || (fstAngle == 0 && sndAngle == 270)) {
                viewer.drawImage(Textures.ANGLED_TAIL.image, 270, snakeColorMap.get(snake),
                        coordinates.multiply(64));
            }
        }
    }

    private void drawWalls(List<Wall> walls) {
        for (Wall wall : walls) {
            for (Coordinates coordinates : wall.getCoordinates()) {
                viewer.drawDefaultImage(Textures.WALL.image, coordinates.multiply(64));
            }
        }
    }

    private void drawApples(List<Apple> apples) {
        for (Apple apple : apples) {
            for (Coordinates coordinates : apple.getCoordinates()) {
                viewer.drawDefaultImage(Textures.APPLE.image, coordinates.multiply(64));
            }
        }
    }

    private void drawGrid() {
        for (int i = 0; i < width / 64; i++) {
            for (int j = 0; j < height / 64; j++) {
                if (i == 0 || i == width / 64 - 1 || j == 0 || j == height / 64 - 1) {
                    viewer.drawDefaultImage(Textures.WALL.image, new Coordinates(i, j).multiply(64));
                }
            }
        }
    }

    private void setResizeListener() {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            width = (int) stage.getWidth();
            height = (int) stage.getHeight();
            model.resize(width / 64, height / 64);
            viewer.resize(width, height);
            draw();
        };
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }
}
