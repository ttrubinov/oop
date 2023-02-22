package ru.nsu.fit.trubinov.primalityChecking;

import java.util.Arrays;

public class ParallelStreamChecker implements ArrayPrimalityChecker {
    @Override
    public boolean isArrayPrime(long[] arr) {
        return Arrays.stream(arr).boxed().toList().parallelStream().noneMatch(this::isComposite);
    }
}
