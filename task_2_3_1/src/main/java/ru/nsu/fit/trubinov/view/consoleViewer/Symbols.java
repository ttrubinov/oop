package ru.nsu.fit.trubinov.view.consoleViewer;

import ru.nsu.fit.trubinov.utils.Direction;
import ru.nsu.fit.trubinov.utils.FieldObject;

import java.util.Map;

import static ru.nsu.fit.trubinov.utils.FieldObject.*;

public class Symbols {
    public static final Map<Direction, Character> headSymbolMap = Map.of(
            Direction.UP, '^',
            Direction.LEFT, '<',
            Direction.DOWN, 'v',
            Direction.RIGHT, '>'
    );

    public static final Map<FieldObject, Character> symbolMap = Map.of(
            SNAKE, '@',
            WALL, '#',
            APPLE, 'O',
            NOTHING, ' '
    );
}
