package ru.nsu.fit.trubinov.primalityChecking;

public class SequentialChecker implements ArrayPrimalityChecker {
    @Override
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
