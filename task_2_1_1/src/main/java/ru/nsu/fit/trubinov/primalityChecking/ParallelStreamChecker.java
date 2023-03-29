package ru.nsu.fit.trubinov.primalityChecking;

import java.util.Arrays;

/**
 * Checking primality of array with parallelStream.
 */
public class ParallelStreamChecker implements ArrayPrimalityChecker {
    public boolean isArrayPrime(long[] arr) {
        return Arrays.stream(arr).boxed().toList().parallelStream().noneMatch(this::isComposite);
    }
}
