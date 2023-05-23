package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.scene.image.Image;

import java.util.Objects;

public enum Textures {
    HEAD("snakeHead.png"),
    TAIL("snakeTail.png"),
    ANGLED_TAIL("snakeAngledTail.png"),
    TAIL_END("snakeEndOfTail.png"),
    FIELD1("field1.png"),
    FIELD2("field2.png"),
    WALL("wall.png"),
    APPLE("apple.png"),
    BEDROCK("bedrock.png");
    private static final String directory = "/textures/";
    public final Image image;

    Textures(String fileName) {
        this.image = new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
                Textures.directory + fileName)),
                64, 64, false, false);
    }
}
