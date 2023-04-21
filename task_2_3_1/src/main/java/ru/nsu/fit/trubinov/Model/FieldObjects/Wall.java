package ru.nsu.fit.trubinov.Model.FieldObjects;

import ru.nsu.fit.trubinov.Model.Field.Coordinates;

import java.util.List;

public record Wall(List<Coordinates> wallCoordinates) {
    public Coordinates intersects(Snake snake) {
        return wallCoordinates.stream().
                filter(coordinates -> snake.getHead().equals(coordinates)).findFirst().orElse(null);
    }
}
