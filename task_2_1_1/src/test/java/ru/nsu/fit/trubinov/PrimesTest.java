package ru.nsu.fit.trubinov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class PrimesTest {
    static long[] primes;
    static long[] primesWithComposite;
    static long[] randomNumbers;

    @BeforeAll
    @Setup
    public static void setUp() throws FileNotFoundException {
        int n = 100000;
        String path = "./src/test/java/ru/nsu/fit/trubinov/";
        System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get(""));
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

    @Test
    @Benchmark
    public void SequentialTest() throws InterruptedException {
        ArrayPrimalityChecker s = new SequentialChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    @Benchmark
    public void Threads4Test() throws InterruptedException {
        ArrayPrimalityChecker s = new ThreadsChecker(4);
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    @Benchmark
    public void Threads12Test() throws InterruptedException {
        ArrayPrimalityChecker s = new ThreadsChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    @Benchmark
    public void ParallelTest() throws InterruptedException {
        ArrayPrimalityChecker s = new ParallelStreamChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }
}
