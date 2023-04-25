package ru.nsu.fit.trubinov;

import org.openjdk.jmh.annotations.*;
import ru.nsu.fit.trubinov.primalityChecking.ArrayPrimalityChecker;
import ru.nsu.fit.trubinov.primalityChecking.MultithreadingChecker;
import ru.nsu.fit.trubinov.primalityChecking.ParallelStreamChecker;
import ru.nsu.fit.trubinov.primalityChecking.SequentialChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class PrimesBenchmark {
    @Param({"10", "100", "1000", "10000", "100000", "1000000"})
    public static int arrSize;

    static long[] arr;

    @Setup
    public static void setUp() throws FileNotFoundException {
        String path = "./src/test/java/ru/nsu/fit/trubinov/testData/";
        Scanner scanner = new Scanner(new File(path + arrSize + ".txt"));
        arr = new long[arrSize];
        for (int i = 0; i < arrSize; i++) {
            arr[i] = scanner.nextLong();
        }
    }

    @Benchmark
    public void parallelStreamChecker() throws InterruptedException {
        ArrayPrimalityChecker s = new ParallelStreamChecker();
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void sequentialChecker() throws InterruptedException {
        ArrayPrimalityChecker s = new SequentialChecker();
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker2() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(2);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker3() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(3);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker4() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(4);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker5() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(5);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker6() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(6);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker8() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(8);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker10() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(10);
        s.isArrayPrime(arr);
    }

    @Benchmark
    public void multithreadingChecker12() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(12);
        s.isArrayPrime(arr);
    }
}
