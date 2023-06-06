package ru.nsu.fit.trubinov.model.fieldObjects;

import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.List;

/**
 * Object of field.
 */
public interface FieldObject {
    List<Coordinates> getCoordinates();

    /**
     * Check if the field object is out of field bounds.
     *
     * @param width  width of the field
     * @param height height of the field
     * @return true if the field object is out of field bounds
     */
    default boolean isOutOfBounds(int width, int height) {
        for (Coordinates coordinates : this.getCoordinates()) {
            if (coordinates.X() >= width - 1 || coordinates.Y() >= height - 1) {
                return true;
            }
        }
        return false;
    }
}
