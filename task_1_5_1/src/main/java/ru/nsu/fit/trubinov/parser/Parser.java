package ru.nsu.fit.trubinov.parser;

import ru.nsu.fit.trubinov.functions.Function;
import ru.nsu.fit.trubinov.number.Number;

/**
 * Parsing expression.
 */
public interface Parser<N extends Number> {
    /**
     * Get next token in string.
     *
     * @return next token
     */
    String getToken();

    N getNumber(String s);

    boolean isNumber(String s);

    Function<N> getFunction(String token);
}
