package ru.nsu.fit.trubinov;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.primalityChecking.ArrayPrimalityChecker;
import ru.nsu.fit.trubinov.primalityChecking.MultithreadingChecker;
import ru.nsu.fit.trubinov.primalityChecking.ParallelStreamChecker;
import ru.nsu.fit.trubinov.primalityChecking.SequentialChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrimesTest {
    static long[] primes;
    static long[] primesWithComposite;
    static long[] randomNumbers;

    @BeforeAll
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

    @Test
    public void SequentialTest() throws InterruptedException {
        ArrayPrimalityChecker s = new SequentialChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    public void MultithreadingChecker4Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker(4);
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    public void MultithreadingChecker12Test() throws InterruptedException {
        ArrayPrimalityChecker s = new MultithreadingChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }

    @Test
    public void ParallelTest() throws InterruptedException {
        ArrayPrimalityChecker s = new ParallelStreamChecker();
        assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }
}
