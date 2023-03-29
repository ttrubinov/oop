package ru.nsu.fit.trubinov.primalityChecking;

/**
 * Checking array primality.
 */
public class SequentialChecker implements ArrayPrimalityChecker {
    public boolean isArrayPrime(long[] arr) {
        boolean returnVal = false;
        for (long number : arr) {
            if (isComposite(number)) {
                returnVal = true;
                break;
            }
        }
        return !returnVal;
    }
}
