package ru.nsu.fit.trubinov.presenter.javaFXPresenter;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public enum SnakeColor {
    YELLOW(Color.YELLOW), BLUE(Color.BLUE), PURPLE(Color.PURPLE), AQUA(Color.AQUA), RED(Color.RED);

    public final Color color;

    SnakeColor(Color color) {
        this.color = color;
    }

    public static List<SnakeColor> getAllSnakeColorsRandomly() {
        List<SnakeColor> allColors = new ArrayList<>(Stream.of(SnakeColor.values()).toList());
        Collections.shuffle(allColors);
        return allColors;
    }

    public static SnakeColor getRandomSnakeColor() {
        return getAllSnakeColorsRandomly().get(0);
    }
}
