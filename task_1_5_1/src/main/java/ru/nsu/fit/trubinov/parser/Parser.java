package ru.nsu.fit.trubinov.parser;

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
}
