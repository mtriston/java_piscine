package edu.school21.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    private final NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 13, 5113, 1046527, 1073676287})
    public void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 66, 1293293, 26142769})
    public void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }
}
