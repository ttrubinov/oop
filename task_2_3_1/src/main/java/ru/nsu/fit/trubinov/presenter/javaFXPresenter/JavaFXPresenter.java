package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private final ImageView snakeImageView = new ImageView(snakeHeadImage);
    private final Stage stage;
    private final Group player = new Group(snakeImageView);
    protected int width;
    protected int height;
    private GraphicsContext gc;
    private Group root;
    private Canvas canvas;
    private Model model;
    private int difficultyLevel = 5;
    private int angle = 270;

    public JavaFXPresenter(Stage stage) {
        this.stage = stage;
        width = 512;
        height = 512;
        model = new Model(width / 64, height / 64, difficultyLevel);
    }

    protected void start() {
        initScene();
//        root.getChildren().add(player);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5),
                new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent event) {
                        // pollInput
                        draw();
                        Coordinates intersection = model.makeMove();
                        draw();
                        if (intersection != null) {
                            stage.close();
                        }
                    }

                }
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void draw() {
        drawField();
        drawUserSnake(model.getUserSnake());
        model.getBotSnakes().forEach(this::drawBotSnake);
        drawWalls(model.getWalls());
        drawApples(model.getApples());
        drawGrid();
//        int fromX = model.getUserSnake().getPrevHeadPosition().multiply(64).X();
//        int toX = model.getUserSnake().getHead().multiply(64).X();
//        int fromY = model.getUserSnake().getPrevHeadPosition().multiply(64).Y();
//        int toY = model.getUserSnake().getHead().multiply(64).Y();
//        doTransitionAnimation(fromX, toX, fromY, toY, player, Duration.millis(500));
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
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP, W:
                if (model.getUserSnake().isPossibleTurn(Direction.UP)) {
                    model.getUserSnake().setDirection(Direction.UP);
                }
                break;
            case DOWN, S:
                if (model.getUserSnake().isPossibleTurn(Direction.DOWN)) {
                    model.getUserSnake().setDirection(Direction.DOWN);
                }
                break;
            case LEFT, A:
                if (model.getUserSnake().isPossibleTurn(Direction.LEFT)) {
                    model.getUserSnake().setDirection(Direction.LEFT);
                }
                break;
            case RIGHT, D:
                if (model.getUserSnake().isPossibleTurn(Direction.RIGHT)) {
                    model.getUserSnake().setDirection(Direction.RIGHT);
                }
                break;
        }
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

    private void drawUserSnake(Snake snake) {
        gc.drawImage(getRotatedSnake(snake.getDirection().angle), snake.getHead().X() * 64, snake.getHead().Y() * 64);
        List<Integer> angles = new ArrayList<>();
        for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
            angles.add(Coordinates.getAngle2(snake.getCoordinates().get(i - 1), snake.getCoordinates().get(i + 1),
                    snake.getCoordinates().get(i)));
        }

        if (snake.getCoordinates().size() > 1) {
            for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
                if (angles.get(i - 1) != null) {
                    gc.drawImage(getRotatedImage(angles.get(i - 1), snakeAngledTailImage),
                            snake.getCoordinates().get(i).X() * 64,
                            snake.getCoordinates().get(i).Y() * 64);
                } else {
                    gc.drawImage(getRotatedImage(Coordinates.getAngle(snake.getCoordinates().get(i),
                                    snake.getCoordinates().get(i - 1)), snakeTailImage),
                            snake.getCoordinates().get(i).X() * 64,
                            snake.getCoordinates().get(i).Y() * 64);
                }
            }
            gc.drawImage(getRotatedImage(
                            Coordinates.getAngle(snake.getCoordinates().get(0), snake.getCoordinates().get(1)),
                            snakeEndOfTailImage),
                    snake.getCoordinates().get(0).X() * 64, snake.getCoordinates().get(0).Y() * 64);
        }
    }

    private void drawBotSnake(Snake snake) {
        gc.drawImage(getRotatedImage(snake.getDirection().angle, botSnakeHeadImage),
                snake.getHead().X() * 64, snake.getHead().Y() * 64);
        List<Integer> angles = new ArrayList<>();
        for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
            angles.add(Coordinates.getAngle2(snake.getCoordinates().get(i - 1), snake.getCoordinates().get(i + 1),
                    snake.getCoordinates().get(i)));
        }

        if (snake.getCoordinates().size() > 1) {
            for (int i = 1; i < snake.getCoordinates().size() - 1; i++) {
                if (angles.get(i - 1) != null) {
                    gc.drawImage(getRotatedImage(angles.get(i - 1), snakeAngledTailImage),
                            snake.getCoordinates().get(i).X() * 64,
                            snake.getCoordinates().get(i).Y() * 64);
                } else {
                    gc.drawImage(getRotatedImage(Coordinates.getAngle(snake.getCoordinates().get(i),
                                    snake.getCoordinates().get(i - 1)), botSnakeTailImage),
                            snake.getCoordinates().get(i).X() * 64,
                            snake.getCoordinates().get(i).Y() * 64);
                }
            }
            gc.drawImage(getRotatedImage(
                            Coordinates.getAngle(snake.getCoordinates().get(0), snake.getCoordinates().get(1)),
                            snakeEndOfTailImage),
                    snake.getCoordinates().get(0).X() * 64, snake.getCoordinates().get(0).Y() * 64);
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

    private void doTransitionAnimation(int fromX, int toX, int fromY, int toY, Group group, Duration duration) {
        root.getChildren().set(1, group);
        TranslateTransition transition = new TranslateTransition(duration, group);
        transition.setFromX(fromX);
        transition.setToX(toX);
        transition.setFromY(fromY);
        transition.setToY(toY);
        transition.play();
    }

    private void doRotationAnimation(int angle, Group group, Duration duration) {
        root.getChildren().set(1, group);
        RotateTransition transition = new RotateTransition(duration, group);
        transition.setByAngle(angle);
        transition.play();
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
