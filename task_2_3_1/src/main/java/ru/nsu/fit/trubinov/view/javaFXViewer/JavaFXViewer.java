package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ru.nsu.fit.trubinov.utils.Coordinates;

/**
 * Drawing with JavaFX.
 */
public class JavaFXViewer {
    public GraphicsContext gc;

    public JavaFXViewer(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    /**
     * Draw image without any adjustments.
     *
     * @param image       image to draw
     * @param coordinates coordinates of the image
     */
    public void drawDefaultImage(Image image, Coordinates coordinates) {
        gc.drawImage(image, coordinates.X(), coordinates.Y());
    }

    /**
     * Draw image with angle and color adjustments.
     *
     * @param image       image to draw
     * @param angle       angle to rotate the image
     * @param hue         hue to adjust the image on
     * @param coordinates coordinates of the image
     */
    public void drawImage(Image image, int angle, double hue, Coordinates coordinates) {
        ImageView imageView = new ImageView(image);
        imageView.setRotate(angle);
        if (hue != 0) {
            imageView.setEffect(new ColorAdjust(hue, 0.1, 0, 0.2));
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        gc.drawImage(imageView.snapshot(params, null), coordinates.X(), coordinates.Y());
    }

    /**
     * Draw borders around the field.
     *
     * @param canvas canvas to draw borders on
     */
    public void drawBedrockBorders(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < canvas.getWidth() / 64; i++) {
            for (int j = 0; j < canvas.getHeight() / 64; j++) {
                gc.drawImage(Textures.BEDROCK.image, i * 64, j * 64);
            }
        }
    }
}
