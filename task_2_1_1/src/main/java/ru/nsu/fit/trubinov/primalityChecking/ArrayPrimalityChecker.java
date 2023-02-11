package ru.nsu.fit.trubinov.primalityChecking;

public interface ArrayPrimalityChecker {
    default boolean isComposite(long number) {
        for (long i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    boolean isArrayPrime(long[] arr) throws InterruptedException;
}
