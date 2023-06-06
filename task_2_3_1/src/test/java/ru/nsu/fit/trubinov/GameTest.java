package ru.nsu.fit.trubinov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.model.Model;

public class GameTest {
    private static Model model;

    @BeforeAll
    public static void setup() {
        model = new Model(5, 5, 1, null);
        model.makeMove();
    }

    @Test
    void eatingTest() {
        model.makeMove();
        Assertions.assertEquals(3, (int) model.getBotSnakes().get(0).length);
    }
}
