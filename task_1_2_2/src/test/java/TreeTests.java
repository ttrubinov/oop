import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.Tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static java.lang.Integer.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.*;

public class TreeTests {
    private final int testSize = 1000;
    private Tree<Integer> tree;
    private ArrayList<Integer> list;

    @BeforeEach
    void forEach() {
        tree = new Tree<>();
        list = new ArrayList<>();
        for (int i = 0; i < testSize; i++) {
            tree.add(i);
            list.add(i);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testSize() {
        assertEquals(tree.size(), testSize);
        tree.clear();
        assertEquals(tree.size(), 0);
    }

    @Test
    void testIsEmpty() {
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    void testAddAndContains() {
        for (int i = 0; i < testSize; i++) {
            tree.add(i + testSize);
        }
        for (int i = 0; i < testSize * 2; i++) {
            assertTrue(tree.contains(i));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testIterator() {
        var DFS = tree.iterator();
        for (int i = 0; i < testSize; i++) {
            assertTrue(DFS.hasNext());
            assertEquals(i, DFS.next());
        }
        assertFalse(DFS.hasNext());
        var DFS2 = tree.iterator();
        for (int i = 0; i < testSize; i++) {
            DFS2.remove();
        }
        assertTrue(tree.isEmpty());
        assertThrows(ConcurrentModificationException.class, DFS2::hasNext);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testIteratorBFS() {
        var BFS = tree.iteratorBFS();
        for (int i = 0; i < testSize; i++) {
            assertTrue(BFS.hasNext());
            assertEquals(i, BFS.next());
        }
        assertFalse(BFS.hasNext());
        var BFS2 = tree.iteratorBFS();
        for (int i = 0; i < testSize; i++) {
            BFS2.remove();
        }
        assertTrue(tree.isEmpty());
        assertThrows(ConcurrentModificationException.class, BFS2::hasNext);
    }

    @Test
    void testToArray() {
        int[] sourceArr = new int[]{0, 1, 2, MAX_VALUE};
        tree.clear();
        tree.add(0);
        tree.add(1);
        tree.add(2);
        tree.add(MAX_VALUE);
        var arr = tree.toArray();
        for (int i = 0; i < 4; i++) {
            assertEquals(arr[i], sourceArr[i]);
        }
    }

    @Test
    void testRemove() {
        for (int i = 0; i < testSize; i++) {
            assertTrue(tree.remove(i));
        }
        assertFalse(tree.remove(228));
        assertTrue(tree.isEmpty());
    }

    @Test
    void testAddAndContainsAll() {
        tree.clear();
        tree.addAll(list);
        assertTrue(tree.containsAll(list));
        list.add(-500);
        assertFalse(tree.containsAll(list));
        list.clear();
        assertTrue(tree.containsAll(list));
        tree.clear();
        assertTrue(tree.containsAll(list));
    }

    @Test
    void testRemoveAll() {
        tree.removeAll(list);
        assertTrue(tree.isEmpty());
        tree.add(testSize + 228);
        tree.removeAll(list);
        assertFalse(tree.isEmpty());
    }

    @Test
    void testRetainAll() {
        tree.retainAll(list);
        assertEquals(tree.size(), testSize);
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        list2.add(testSize + 228);
        tree.retainAll(list2);
        assertEquals(tree.size(), 2);
    }

    @Test
    void testClear() {
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    void testExceptions() {
        assertThrows(NullPointerException.class, () -> tree.remove(null));
        assertThrows(NullPointerException.class, () -> tree.retainAll(null));
        assertThrows(NullPointerException.class, () -> tree.add(null));
        assertThrows(NullPointerException.class, () -> tree.addAll(null));
    }
}
