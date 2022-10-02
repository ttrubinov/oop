import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.Stack;

import java.util.EmptyStackException;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StackTests {
    @Test
    public void testPopThrows() {
        Stack<Integer> testSt = new Stack<>();
        assertThrows(EmptyStackException.class, testSt::pop);
    }

    @Test
    public void testPushStackThrows() {
        Stack<Integer> testSt = new Stack<>();
        assertThrows(NullPointerException.class, () -> testSt.pushStack(null));
    }

    @Test
    public void testPopStackThrows() {
        Stack<Integer> testSt = new Stack<>();
        assertThrows(IllegalArgumentException.class, () -> testSt.popStack(-1));
        assertThrows(EmptyStackException.class, () -> testSt.popStack(1));
    }

    @Test
    public void testPushPopCnt() {
        Random rand = new Random();
        Stack<Integer> testSt = new Stack<>();
        int[] arr = new int[100000];
        for (int i = 0; i < 100000; i++) {
            int x = rand.nextInt(MAX_VALUE);
            arr[i] = x;
            testSt.push(x);
        }
        for (int i = 100000 - 1; i >= 0; i--) {
            assertEquals(testSt.pop(), arr[i]);
            assertEquals(testSt.count(), i);
        }
    }

    @Test
    public void testPushStackAndPopStack() {
        Random rand = new Random();
        Stack<Integer> testSt = new Stack<>();
        Stack<Integer> testStToPush = new Stack<>();
        Stack<Integer> testStToPop;
        int[] arr = new int[100000];
        for (int i = 0; i < 100000; i++) {
            int x = rand.nextInt(MAX_VALUE);
            testStToPush.push(x);
            arr[i] = x;
        }
        for (int i = 0; i < 10; i++) {
            testSt.pushStack(testStToPush);
        }
        assertEquals(testSt.count(), 1000000);
        testStToPop = testSt.popStack(100000);
        for (int i = 0; i < 100000; i++) {
            assertEquals(testStToPush.pop(), arr[100000 - i - 1]);
            assertEquals(testStToPop.pop(), arr[i]);
        }
    }
}
