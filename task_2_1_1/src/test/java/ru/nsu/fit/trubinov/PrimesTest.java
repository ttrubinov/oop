package ru.nsu.fit.trubinov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.trubinov.primalityChecking.ArrayPrimalityChecker;
import ru.nsu.fit.trubinov.primalityChecking.MultithreadingChecker;
import ru.nsu.fit.trubinov.primalityChecking.ParallelStreamChecker;
import ru.nsu.fit.trubinov.primalityChecking.SequentialChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PrimesTest {
    static long[] primes;
    static long[] primesWithComposite;
    static long[] randomNumbers;

    public static Stream<ArrayPrimalityChecker> primalityCheckingAlgorithms() {
        return Stream.of(new SequentialChecker(), new MultithreadingChecker(1),
                new MultithreadingChecker(4), new MultithreadingChecker(), new ParallelStreamChecker());
    }

    @BeforeAll
    public static void setUp() throws FileNotFoundException {
        String path = "./src/test/java/ru/nsu/fit/trubinov/testData/";
        int n = 10000;
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


    @ParameterizedTest
    @MethodSource("primalityCheckingAlgorithms")
    void finderTest(ArrayPrimalityChecker s) throws InterruptedException {
        Assertions.assertTrue(s.isArrayPrime(primes));
        assertFalse(s.isArrayPrime(primesWithComposite));
        assertFalse(s.isArrayPrime(randomNumbers));
    }
}
