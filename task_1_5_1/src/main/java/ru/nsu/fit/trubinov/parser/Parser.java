package ru.nsu.fit.trubinov.parser;

import ru.nsu.fit.trubinov.functions.*;
import ru.nsu.fit.trubinov.number.ComplexNumber;

/**
 * Parsing expression.
 */
public interface Parser {
    /**
     * Get next token in string.
     *
     * @return next token
     */
    String getToken();

    default Function<ComplexNumber> getFunction(String token) {
        return switch (token) {
            case "+" -> new Add();
            case "-" -> new Subtract();
            case "*" -> new Multiply();
            case "/" -> new Divide();
            case "log" -> new Logarithm();
            case "sin" -> new Sinus();
            case "cos" -> new Cosine();
            default -> throw new IllegalArgumentException();
        };
    }
}
