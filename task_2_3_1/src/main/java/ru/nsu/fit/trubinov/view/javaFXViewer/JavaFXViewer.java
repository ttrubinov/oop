package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.Objects;

public class JavaFXViewer {
    public GraphicsContext gc;
    private Scene scene;
    private Group root;
    @FXML
    private Canvas canvas;

    public JavaFXViewer() {
    }

    public Scene getScene() {
        return scene;
    }

    public void initScene(Stage stage, int width, int height) {
        root = new Group();
        stage.setTitle("Best Snake game");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png"))));
        canvas = new Canvas(width, height);
        stage.setResizable(true);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        this.scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void resize(int width, int height) {
        root.resize(width, height);
        canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().set(0, canvas);
    }

    public void drawImage(Image image, Coordinates coordinates) {
        gc.drawImage(image, coordinates.X(), coordinates.Y());
    }

    public void drawRotatedImage(Image image, int angle, Coordinates coordinates) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(angle);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
    }

    public void drawColoredImage(Image image, Color color, Coordinates coordinates) {
        ImageView imageView = new ImageView(image);
        imageView.setEffect(new ColorAdjust(color.getHue() / 180 - 1, 0, 0, 0.2));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
    }

    public void drawRotatedColoredImage(Image image, int angle, Color color, Coordinates coordinates) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(angle);
        imageView.setEffect(new ColorAdjust(color.getHue() / 180 - 1, 0, 0, 0.2));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
    }
}
