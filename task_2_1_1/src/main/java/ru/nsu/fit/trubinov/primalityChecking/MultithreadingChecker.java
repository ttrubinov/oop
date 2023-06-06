package ru.nsu.fit.trubinov.primalityChecking;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Checking array primality in parallel threads
 */
public class MultithreadingChecker implements ArrayPrimalityChecker {
    public int threadCount;

    public MultithreadingChecker() {
        this.threadCount = Runtime.getRuntime().availableProcessors();
    }

    public MultithreadingChecker(int threadCount) {
        this.threadCount = threadCount;
    }

    public boolean isArrayPrime(long[] arr) {
        Thread[] threads = new Thread[threadCount];
        AtomicBoolean returnVal = new AtomicBoolean(false);
        for (int j = 0; j < threadCount; j++) {
            int threadIndex = j;
            threads[j] = new Thread(() -> {
                for (int i = threadIndex; i < arr.length; i += threadCount) {
                    if (isComposite(arr[i])) {
                        returnVal.set(true);
                        for (Thread thread : threads) {
                            if (thread != null) {
                                thread.interrupt();
                            }
                        }
                        break;
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
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return !returnVal.get();
    }
}