package ru.nsu.fit.trubinov.Model.Field;

import java.util.ArrayList;

public record Grid(int width, int height) {
    public int size() {
        return width * height;
    }

    public ArrayList<Coordinates> arrayOfCells() {
        ArrayList<Coordinates> array = new ArrayList<>();
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                array.add(new Coordinates(i, j));
            }
        }
        return array;
    }
}
