package ru.nsu.fit.trubinov.number;

/**
 * Interface for number and functions, which can be applied to this number.
 *
 * @param <T> class that implements this interface
 */
public interface Number<T extends Number<T>> {
    /**
     * String representation of the number.
     *
     * @return string
     */
    String toString();


    /**
     * Checks if this number equals to another.
     *
     * @param b another number
     * @return true if numbers is equal
     */
    boolean equals(T b);

    /**
     * Addition of numbers.
     *
     * @param b number to add
     * @return result number
     */
    T add(T b);

    /**
     * Subtraction of numbers.
     *
     * @param b number to add
     * @return result number
     */
    T sub(T b);

    /**
     * Multiplication of numbers.
     *
     * @param b number to add
     * @return result number
     */
    T mul(T b);

    /**
     * Division of numbers.
     *
     * @param b number to add
     * @return result number
     */
    T div(T b);

    /**
     * Logarithm of number.
     *
     * @return result number
     */
    T log();

    /**
     * Cosine of number.
     *
     * @return result number
     */
    T cos();

    /**
     * Sinus of number.
     *
     * @return result number
     */
    T sin();
}
