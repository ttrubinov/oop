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
    static long[] primes;
    static long[] primesWithComposite;
    static long[] randomNumbers;

    @Setup
    public static void setUp() throws FileNotFoundException {
        int n = 100000;
        String path = "./src/test/java/ru/nsu/fit/trubinov/testData/";
        Scanner scanner = new Scanner(new File(path + "primes.txt"));
        primes = new long[n];
        for (int i = 0; i < n; i++) {
            primes[i] = scanner.nextLong();
        }
        scanner = new Scanner(new File(path + "primesAndComposite.txt"));
        primesWithComposite = new long[n];
        for (int i = 0; i < n; i++) {
            primesWithComposite[i] = scanner.nextLong();
        }
        scanner = new Scanner(new File(path + "randomNumbers.txt"));
        randomNumbers = new long[n];
        for (int i = 0; i < n; i++) {
            randomNumbers[i] = scanner.nextLong();
        }
    }

    @Benchmark
    public void SequentialTest() throws InterruptedException {
        ArrayPrimalityChecker s = new SequentialChecker();
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker1Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(1);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker2Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(2);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker3Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(3);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker4Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(4);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker5Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(5);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker6Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(6);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker7Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(7);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker8Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(8);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker9Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(9);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker10Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(10);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker11Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(11);
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void MultithreadingChecker12Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker();
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }

    @Benchmark
    public void ParallelTest() throws InterruptedException {
        ArrayPrimalityChecker s = new ParallelStreamChecker();
        s.isArrayPrime(primes);
        s.isArrayPrime(primesWithComposite);
        s.isArrayPrime(randomNumbers);
    }
}