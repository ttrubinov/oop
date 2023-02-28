package ru.nsu.fit.trubinov.primalityChecking;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadingChecker implements ArrayPrimalityChecker {
    public int threadCount;

    public MultithreadingChecker() {
        this.threadCount = Runtime.getRuntime().availableProcessors();
    }

    public MultithreadingChecker(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public boolean isArrayPrime(long[] arr) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        AtomicBoolean returnVal = new AtomicBoolean(false);
        AtomicInteger ind = new AtomicInteger(0);
        for (int j = 0; j < threadCount; j++) {
            threads[j] = new Thread(() -> {
                int curIndex;
                while (!returnVal.get() && ((curIndex = ind.incrementAndGet() - 1) < arr.length)) {
                    if (isComposite(arr[curIndex])) {
                        returnVal.set(true);
                    }
                }
            });
            if (!returnVal.get()) {
                threads[j].start();
            }
        }
        for (Thread thread : threads) {
            if (thread != null) {
                if (!returnVal.get()) {
                    thread.join();
                } else {
                    thread.interrupt();
                }
            }
        }
        return !returnVal.get();
    }
}
