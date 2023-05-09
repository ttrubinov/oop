package ru.nsu.fit.trubinov.model.fieldObjects;

import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.List;

public interface FieldObject {
    List<Coordinates> getCoordinates();

    default boolean isOutOfBounds(int width, int height) {
        for (Coordinates coordinates : this.getCoordinates()) {
            if (coordinates.X() >= width - 1 || coordinates.Y() >= height - 1) {
                return true;
            }
        }
        return false;
    }
}
