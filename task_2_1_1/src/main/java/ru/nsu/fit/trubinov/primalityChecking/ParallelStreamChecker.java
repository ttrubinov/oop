package ru.nsu.fit.trubinov.primalityChecking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParallelStreamChecker implements ArrayPrimalityChecker {
    @Override
    public boolean isArrayPrime(long[] arr) {
        AtomicBoolean returnVal = new AtomicBoolean(false);
        List<Long> list = new ArrayList<>(arr.length);
        for (long n : arr) {
            list.add(n);
        }
        Runnable doNothing = () -> {
        };
        list.parallelStream().takeWhile(number -> {
            if (isComposite(number)) {
                returnVal.set(true);
                return false;
            } else {
                return true;
            }
        }).forEach(x -> doNothing.run());
        return !returnVal.get();
    }
}
