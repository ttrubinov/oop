package ru.nsu.fit.trubinov.view.javaFXViewer;

import javafx.scene.image.Image;

import java.util.Objects;

public enum Textures {
    HEAD(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.HEAD.name)))),
    TAIL(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.TAIL.name)))),
    ANGLED_TAIL(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.ANGLED_TAIL.name)))),
    TAIL_END(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.TAIL_END.name)))),
    FIELD1(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.FIELD1.name)))),
    FIELD2(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.FIELD2.name)))),
    WALL(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.WALL.name)))),
    APPLE(new Image(Objects.requireNonNull(JavaFXViewer.class.getResourceAsStream(
            TextureName.directory + TextureName.APPLE.name)),
            64, 64, false, false)),
    BEDROCK(new Image(Objects.requireNonNull(
            JavaFXViewer.class.getResourceAsStream(
                    TextureName.directory + TextureName.BEDROCK.name
            )),
            64, 64, false, false));
    public final Image image;

    Textures(Image image) {
        this.image = image;
    }

    private enum TextureName {
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
        private final String name;

        TextureName(String name) {
            this.name = name;
        }
    }
}
