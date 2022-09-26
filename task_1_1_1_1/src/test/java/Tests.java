import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.Heap;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {
    @Test
    void tests() {
        int[] testArr1 = {};
        int[] testArr2 = {};
        Heap.heapSort(testArr1);
        Arrays.sort(testArr2);
        assertArrayEquals(testArr1, testArr2);

        int[] testArr = null;
        assertThrows(NullPointerException.class, () -> Heap.heapSort(testArr));

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
