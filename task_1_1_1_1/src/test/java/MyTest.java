import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.trubinov.Heap;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyTest {
    public static Stream<int[]> specialCases() {
        return Stream.of(new int[]{0}, new int[]{1}, new int[]{-1}, new int[]{1, 2, 3, 4, 5},
                new int[]{5, 4, 3, 2, 1}, new int[]{-1, -2, -3, -4, -5},
                new int[]{-5, -4, -3, -2, -1}, new int[]{MAX_VALUE, MAX_VALUE, MAX_VALUE},
                new int[]{MIN_VALUE, MIN_VALUE, MIN_VALUE},
                new int[]{MIN_VALUE, MAX_VALUE, MIN_VALUE, MAX_VALUE}, new int[]{0, 0, 0, 0, 0},
                new int[]{5, 5, 5, 5}, new int[]{-4, -4, -4, -4, -4}, new int[]{});
    }

    @ParameterizedTest
    @MethodSource("specialCases")
    public void testSpecialCases(int[] arr1) {
        int[] arr2 = new int[arr1.length];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        Heap.heapSort(arr1);
        Arrays.sort(arr2);
        assertArrayEquals(arr1, arr2);
    }

    @Test
    void testNullPointer() {
        int[] testArr = null;
        assertThrows(NullPointerException.class, () -> Heap.heapSort(testArr));
    }

    @Test
    void testRandomArrays() {
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            int len = rand.nextInt(10000);
            int[] arr1 = new int[len];
            int[] arr2 = new int[len];
            for (int j = 0; j < len; j++) {
                arr1[j] = rand.nextInt(1000000000);
                int s = rand.nextInt(2);
                if (s == 0) {
                    arr1[j] *= -1;
                }
                arr2[j] = arr1[j];
            }
            Heap.heapSort(arr1);
            Arrays.sort(arr2);
            assertArrayEquals(arr1, arr2);
        }
    }
}
