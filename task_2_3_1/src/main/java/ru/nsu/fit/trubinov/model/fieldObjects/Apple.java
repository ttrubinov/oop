package ru.nsu.fit.trubinov.model.fieldObjects;

import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.List;

/**
 * Apple, which can be eaten by snake.
 *
 * @param coordinates coordinates of the apple
 */
public record Apple(Coordinates coordinates) implements FieldObject {
    public List<Coordinates> getCoordinates() {
        return List.of(coordinates);
    }
}
