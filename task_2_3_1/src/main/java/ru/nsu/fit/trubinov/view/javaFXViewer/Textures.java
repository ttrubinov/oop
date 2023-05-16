package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.scene.image.Image;

import java.util.Objects;

public enum Textures {
    HEAD("/snakeHead.png"),
    TAIL("/snakeTail.png"),
    ANGLED_TAIL("/snakeAngledTail.png"),
    TAIL_END("/snakeEndOfTail.png"),
    FIELD1("/field1.png"),
    FIELD2("/field2.png"),
    WALL("/wall.png"),
    APPLE("/apple.png"),
    BEDROCK("/bedrock.png");

    private static final String texturesDirectory = "/textures";
    private final String filename;

    Textures(String filename) {
        this.filename = filename;
    }

    private String getFilename() {
        return texturesDirectory + this.filename;
    }

    public Image getImage() {
        return new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(this.getFilename())));
    }
}
