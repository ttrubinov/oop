package ru.nsu.fit.trubinov.Model.FieldObjects;

import ru.nsu.fit.trubinov.Model.Field.Coordinates;

import java.util.ArrayList;

public record Wall(ArrayList<Coordinates> wallCoordinates) {
    public boolean intersects(Snake snake) {
        return wallCoordinates.stream().anyMatch(coordinates -> snake.getHead().equals(coordinates));
    }
}
