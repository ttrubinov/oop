package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.fit.trubinov.model.Model;
import ru.nsu.fit.trubinov.model.field.Direction;
import ru.nsu.fit.trubinov.model.fieldObjects.Apple;
import ru.nsu.fit.trubinov.model.fieldObjects.Wall;
import ru.nsu.fit.trubinov.model.fieldObjects.snake.Snake;
import ru.nsu.fit.trubinov.presenter.Presenter;
import ru.nsu.fit.trubinov.utils.Coordinates;
import ru.nsu.fit.trubinov.view.javaFXViewer.JavaFXViewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// TODO: View functions to JavaFXView
public class JavaFXPresenter implements Presenter {
    public static final Image snakeHeadImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/snakeHead2.png")));
    public static final Image snakeTailImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/snakeTail2.png")));
    public static final Image botSnakeTailImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/botSnakeTail.png")));
    public static final Image botSnakeHeadImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/botSnakeHead.png")));
    public static final Image snakeAngledTailImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/snakeAngledTail2.png")));
    public static final Image snakeEndOfTailImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/snakeEndOfTail.png")));
    private final Image field1Image = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/leftField.png")));
    private final Image field2Image = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/rightField2.png")));
    private final Image wallImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/wall.png")));
    private final Image appleImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/apple.png")),
            64, 64, false, false);
    private final Image bedrockImage = new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream("/textures/bedrock.png")),
            64, 64, false, false);

    private final Stage stage;
    protected int width;
    protected int height;
    private Model model;
    private GraphicsContext gc;
    private Group root;
    @FXML
    private Canvas canvas;
    private int difficultyLevel = 5;
    private Direction lastDirection;

    public JavaFXPresenter(Stage stage) {
        this.stage = stage;
        width = 512;
        height = 512;
        model = new Model(width / 64, height / 64, difficultyLevel);
        lastDirection = model.getUserSnake().getDirection();
    }

    protected void start() {
        initScene();
        EventHandler<ActionEvent> eventHandler = null;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500),
                new EventHandler<>() {
                    final List<Long> listOfTimes = new ArrayList<>();
                    long prevTime = System.currentTimeMillis();

                    public void handle(ActionEvent event) {
                        changeDirection();
                        Coordinates intersection = model.makeMove();
                        draw();
                        long curTime = System.currentTimeMillis();
                        long sum = 0;
                        listOfTimes.add(curTime - prevTime);
                        for (long val : listOfTimes) {
                            sum += val;
                        }
//                        System.out.println(sum / listOfTimes.size());
                        prevTime = curTime;
                        if (listOfTimes.size() > 30) {
                            listOfTimes.remove(0);
                        }
                        if (intersection != null) {
                            stage.close();
                            System.out.println("AAA");
                        }
                    }
                }));
//        eventHandler.handle(eventHandler);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void draw() {
        drawField();
        drawSnake(model.getUserSnake());
        model.getBotSnakes().forEach(this::drawSnake);
        drawWalls(model.getWalls());
        drawApples(model.getApples());
        drawGrid();
    }


    public void initScene() {
        root = new Group();
        stage.setTitle("Best Snake game");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        canvas = new Canvas(width, height);
        stage.setResizable(true);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(this::onKeyPressed);
        stage.setScene(scene);
        setResizeListener();
        stage.show();
        model = new Model(width / 64, height / 64, difficultyLevel);
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        Direction direction = getDirectionByKeyEvent(keyEvent);
        if (direction != null) {
            lastDirection = direction;
        }
    }

    private Direction getDirectionByKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP, W -> {
                if (model.getUserSnake().isPossibleTurn(Direction.UP)) {
                    return Direction.UP;
                }
            }
            case DOWN, S -> {
                if (model.getUserSnake().isPossibleTurn(Direction.DOWN)) {
                    return Direction.DOWN;
                }
            }
            case LEFT, A -> {
                if (model.getUserSnake().isPossibleTurn(Direction.LEFT)) {
                    return Direction.LEFT;
                }
            }
            case RIGHT, D -> {
                if (model.getUserSnake().isPossibleTurn(Direction.RIGHT)) {
                    return Direction.RIGHT;
                }
            }
        }
        return null;
    }

    private void changeDirection() {
        model.getUserSnake().setDirection(lastDirection);
    }

    private void drawField() {
        for (int i = 0; i < width; i += 64) {
            for (int j = 0; j < height; j += 64) {
                if (i >= width - width % 64 || j >= height - height % 64) {
                    gc.drawImage(bedrockImage, i, j);
                } else {
                    if ((i + j) % 128 == 0) gc.drawImage(field1Image, i, j);
                    else gc.drawImage(field2Image, i, j);
                }
            }
        }
    }

    private void drawSnake(Snake snake) {
        gc.drawImage(getRotatedSnake(snake.getDirection().angle), snake.getHead().X() * 64, snake.getHead().Y() * 64);
        if (snake.getCoordinates().size() > 1) {
            gc.drawImage(getRotatedImage(snake.getSnakeCeilDirection(snake.getCoordinates().get(1)).angle,
                            snakeEndOfTailImage),
                    snake.getCoordinates().get(0).X() * 64, snake.getCoordinates().get(0).Y() * 64);
        }
        for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
            Coordinates coordinates = snake.getCoordinates().get(i);
            int fstAngle;
            int sndAngle;
            try {
                fstAngle = snake.getSnakeCeilDirection(coordinates).angle;
                sndAngle = snake.getSnakeCeilDirection(snake.getCoordinates().get(i + 1)).angle;
            } catch (Exception e) {
                System.out.println(snake.length + " " + snake.getCoordinates().size() + " " + snake.getCoordinates());
                System.out.println(snake.snakeCeilDirections);
                System.out.println(model.field);
                throw new RuntimeException();
            }
            if (fstAngle == sndAngle) {
                gc.drawImage(getRotatedImage(snake.getSnakeCeilDirection(coordinates).angle, snakeTailImage),
                        coordinates.X() * 64, coordinates.Y() * 64);
                continue;
            }
            if ((fstAngle == 180 && sndAngle == 270) || (fstAngle == 90 && sndAngle == 0)) {
                gc.drawImage(snakeAngledTailImage, coordinates.X() * 64, coordinates.Y() * 64);
            } else if ((fstAngle == 270 && sndAngle == 0) || (fstAngle == 180 && sndAngle == 90)) {
                gc.drawImage(getRotatedImage(90, snakeAngledTailImage),
                        coordinates.X() * 64, coordinates.Y() * 64);
            } else if ((fstAngle == 0 && sndAngle == 90) || (fstAngle == 270 && sndAngle == 180)) {
                gc.drawImage(getRotatedImage(180, snakeAngledTailImage),
                        coordinates.X() * 64, coordinates.Y() * 64);
            } else if ((fstAngle == 90 && sndAngle == 180) || (fstAngle == 0 && sndAngle == 270)) {
                gc.drawImage(getRotatedImage(270, snakeAngledTailImage),
                        coordinates.X() * 64, coordinates.Y() * 64);
            }
        }
    }

    private void drawWalls(List<Wall> walls) {
        for (Wall wall : walls) {
            for (Coordinates coordinates : wall.getCoordinates()) {
                gc.drawImage(wallImage, coordinates.X() * 64, coordinates.Y() * 64);
            }
        }
    }

    private void drawApples(List<Apple> apples) {
        for (Apple apple : apples) {
            for (Coordinates coordinates : apple.getCoordinates()) {
                gc.drawImage(appleImage, coordinates.X() * 64, coordinates.Y() * 64);
            }
        }
    }

    private void drawGrid() {
        for (int i = 0; i < width / 64; i++) {
            for (int j = 0; j < height / 64; j++) {
                if (i == 0 || i == width / 64 - 1 || j == 0 || j == height / 64 - 1) {
                    gc.drawImage(wallImage, i * 64, j * 64);
                }
            }
        }
    }

    private Image getRotatedSnake(int angle) {
        ImageView imageView = new ImageView(snakeHeadImage);
        imageView.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    private Image getRotatedImage(int angle, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }

    private void setResizeListener() {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            width = (int) stage.getWidth();
            height = (int) stage.getHeight();
            model.resize(width / 64, height / 64);
            root.resize(width, height);
            canvas = new Canvas(width, height);
            gc = canvas.getGraphicsContext2D();
            root.getChildren().set(0, canvas);
            draw();
        };
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }
}
