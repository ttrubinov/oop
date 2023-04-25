package ru.nsu.fit.trubinov.model.fieldObjects;

import ru.nsu.fit.trubinov.utils.Coordinates;

import java.util.List;

public record Wall(List<Coordinates> coordinates) implements FieldObject {
    public List<Coordinates> getCoordinates() {
        return coordinates;
    }
}
