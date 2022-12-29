package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.Number;

import java.util.List;

/**
 * Function that can be used in calculator.
 *
 * @param <T> type of Number
 */
public interface Function<T extends Number> {
    /**
     * Get arity of the function.
     *
     * @return arity
     */
    int getArity();

    /**
     * Get arguments that should be applied to the function.
     *
     * @return list of arguments
     */
    List<T> getArgs();

    /**
     * Checks if the function can be applied.
     *
     * @return true if the function can be applied
     */
    default  boolean applicable() {
        return getArity() == getArgs().size();
    }

    /**
     * Applying of the function.
     *
     * @return result of the function
     */
    T apply();
}
