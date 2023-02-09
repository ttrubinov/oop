package ru.nsu.fit.trubinov;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadsChecker implements ArrayPrimalityChecker {
    public int threadCount;

    public ThreadsChecker() {
        this.threadCount = 12;
    }

    public ThreadsChecker(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public boolean isArrayPrime(long[] arr) throws InterruptedException {
        Arrays.sort(arr);
        int size = arr.length;
        Thread[] threads = new Thread[threadCount];
        AtomicBoolean returnVal = new AtomicBoolean(false);
        for (int j = 0; j < threadCount; j++) {
            int finalN = j;
            threads[j] = new Thread(() -> {
                for (int i = finalN; i < size; i += threadCount) {
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
                    thread.join();
                } else {
                    thread.interrupt();
                }
            }
        }
        return !returnVal.get();
    }
}
