package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
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

    protected void start() {
        viewer.initScene(stage, width, height);
        init();

//        EventHandler<ActionEvent> eventHandler = null;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200),
                new EventHandler<>() {

                    public void handle(ActionEvent event) {
                        changeDirection();
                        Coordinates intersection = model.makeMove();
                        draw();
                        if (intersection != null) {
//                            System.out.println(model.field);
//                            stage.close();
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
        model.getBotSnakes().forEach(this::drawBotSnake);
        drawWalls(model.getWalls());
        drawApples(model.getApples());
        drawGrid();
    }


    public void init() {
        draw();
        viewer.getScene().setOnKeyPressed(this::onKeyPressed);
        model = new Model(width / 64, height / 64, difficultyLevel);
        setResizeListener();
        snakeColorMap = new HashMap<>();
        model.getBotSnakes().forEach(botSnake -> snakeColorMap.put(botSnake, SnakeColor.getRandomSnakeColor().color));
        setBotSpawnListener();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Direction direction = getDirectionByKeyEvent(keyEvent);
        if (direction != null) {
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
                    drawImage(Textures.BEDROCK.getImage(), new Coordinates(i, j));
                } else {
                    if ((i + j) % (2 * 64) == 0) {
                        drawImage(Textures.FIELD1.getImage(), new Coordinates(i, j));
                    } else {
                        drawImage(Textures.FIELD2.getImage(), new Coordinates(i, j));
                    }
                }
            }
        }
    }

    private void drawSnake(Snake snake) {
        drawRotatedImage(Textures.HEAD.getImage(), snake.getDirection().angle, snake.getHead().multiply(64));
        if (snake.getCoordinates().size() > 1) {
            drawRotatedImage(Textures.TAIL_END.getImage(),
                    snake.getSnakeCeilDirection(snake.getCoordinates().get(1)).angle,
                    snake.getCoordinates().get(0).multiply(64));
        }
        for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
            Coordinates coordinates = snake.getCoordinates().get(i);
            int fstAngle;
            int sndAngle;
            fstAngle = snake.getSnakeCeilDirection(coordinates).angle;
            sndAngle = snake.getSnakeCeilDirection(snake.getCoordinates().get(i + 1)).angle;

            if (fstAngle == sndAngle) {
                drawRotatedImage(Textures.TAIL.getImage(),
                        snake.getSnakeCeilDirection(coordinates).angle, coordinates.multiply(64));
                continue;
            }
            if ((fstAngle == 180 && sndAngle == 270) || (fstAngle == 90 && sndAngle == 0)) {
                drawImage(Textures.ANGLED_TAIL.getImage(), coordinates.multiply(64));
            } else if ((fstAngle == 270 && sndAngle == 0) || (fstAngle == 180 && sndAngle == 90)) {
                drawRotatedImage(Textures.ANGLED_TAIL.getImage(), 90, coordinates.multiply(64));
            } else if ((fstAngle == 0 && sndAngle == 90) || (fstAngle == 270 && sndAngle == 180)) {
                drawRotatedImage(Textures.ANGLED_TAIL.getImage(), 180, coordinates.multiply(64));
            } else if ((fstAngle == 90 && sndAngle == 180) || (fstAngle == 0 && sndAngle == 270)) {
                drawRotatedImage(Textures.ANGLED_TAIL.getImage(), 270, coordinates.multiply(64));
            }
        }
    }

    private void drawBotSnake(Snake snake) {
        drawRotatedColoredImage(Textures.HEAD.getImage(), snake.getDirection().angle,
                snakeColorMap.get(snake), snake.getHead().multiply(64));
        if (snake.getCoordinates().size() > 1) {
            drawRotatedColoredImage(Textures.TAIL_END.getImage(),
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
                drawRotatedColoredImage(Textures.TAIL.getImage(), snake.getSnakeCeilDirection(coordinates).angle,
                        snakeColorMap.get(snake), coordinates.multiply(64));
                continue;
            }
            if ((fstAngle == 180 && sndAngle == 270) || (fstAngle == 90 && sndAngle == 0)) {
                drawColoredImage(Textures.ANGLED_TAIL.getImage(),
                        snakeColorMap.get(snake), coordinates.multiply(64));
            } else if ((fstAngle == 270 && sndAngle == 0) || (fstAngle == 180 && sndAngle == 90)) {
                drawRotatedColoredImage(Textures.ANGLED_TAIL.getImage(), 90,
                        snakeColorMap.get(snake), coordinates.multiply(64));
            } else if ((fstAngle == 0 && sndAngle == 90) || (fstAngle == 270 && sndAngle == 180)) {
                drawRotatedColoredImage(Textures.ANGLED_TAIL.getImage(), 180,
                        snakeColorMap.get(snake), coordinates.multiply(64));

            } else if ((fstAngle == 90 && sndAngle == 180) || (fstAngle == 0 && sndAngle == 270)) {
                drawRotatedColoredImage(Textures.ANGLED_TAIL.getImage(), 270,
                        snakeColorMap.get(snake), coordinates.multiply(64));
            }
        }
    }

    private void drawWalls(List<Wall> walls) {
        for (Wall wall : walls) {
            for (Coordinates coordinates : wall.getCoordinates()) {
                drawImage(Textures.WALL.getImage(), coordinates.multiply(64));
            }
        }
    }

    private void drawApples(List<Apple> apples) {
        for (Apple apple : apples) {
            for (Coordinates coordinates : apple.getCoordinates()) {
                drawImage(Textures.APPLE.getImage(), coordinates.multiply(64));
            }
        }
    }

    private void drawGrid() {
        for (int i = 0; i < width / 64; i++) {
            for (int j = 0; j < height / 64; j++) {
                if (i == 0 || i == width / 64 - 1 || j == 0 || j == height / 64 - 1) {
                    drawImage(Textures.WALL.getImage(), new Coordinates(i, j).multiply(64));
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

    public void drawImage(Image image, Coordinates coordinates) {
        viewer.gc.drawImage(image, coordinates.X(), coordinates.Y());
    }

    public void drawRotatedImage(Image image, int angle, Coordinates coordinates) {
//        ImageView imageView = new ImageView(image);
//        imageView.setRotate(angle);
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//        viewer.gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
        viewer.gc.drawImage(image, coordinates.X(), coordinates.Y());
    }

    public void drawColoredImage(Image image, Color color, Coordinates coordinates) {
//        ImageView imageView = new ImageView(image);
//        imageView.setEffect(new ColorAdjust(color.getHue() / 180 - 1, 0, 0, 0.2));
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//        viewer.gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
        viewer.gc.drawImage(image, coordinates.X(), coordinates.Y());
    }

    public void drawRotatedColoredImage(Image image, int angle, Color color, Coordinates coordinates) {
//        ImageView imageView = new ImageView(image);
//        imageView.setRotate(angle);
//        imageView.setEffect(new ColorAdjust(color.getHue() / 180 - 1, 0, 0, 0.2));
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//        viewer.gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
        viewer.gc.drawImage(image, coordinates.X(), coordinates.Y());
    }
}
