package ru.nsu.fit.trubinov.primalityChecking;

/**
 * Interface for checking array primality.
 */
public interface ArrayPrimalityChecker {
    /**
     * Checking number primality.
     *
     * @param number number to check
     * @return true if number is composite
     */
    default boolean isComposite(long number) {
        for (long i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checking array primality.
     *
     * @param arr array to check
     * @return true if every number in array is prime
     */
    boolean isArrayPrime(long[] arr);
}
