package nsu.fit.trubinov;

public class Heap {
    int[] heap;
    int cur;

    Heap(int size) {
        heap = new int[size];
        cur = 0;
    }

    private void swap(int ind1, int ind2) {
        int temp = heap[ind1];
        heap[ind1] = heap[ind2];
        heap[ind2] = temp;
    }

    private void siftDown(int i) {
        if (2 * i + 1 < cur) {
            int l = 2 * i + 1, r = 2 * i + 2;
            if (r >= cur || heap[l] < heap[r]) {
                if (heap[i] < heap[l]) return;
                swap(i, l);
                siftDown(l);
            } else {
                if (heap[i] < heap[r]) return;
                swap(i, r);
                siftDown(r);
            }
        }
    }

    private void siftUp(int i) {
        if (heap[i] < heap[(i - 1) / 2]) {
            swap(i, (i - 1) / 2);
            siftUp((i - 1) / 2);
        }
    }

    private void insert(int a) {
        heap[cur] = a;
        cur++;
        siftUp(cur - 1);
    }

    private int extractMin() {
        int min = heap[0];
        swap(0, cur - 1);
        cur--;
        siftDown(0);
        return min;
    }

    public static void heapSort(int[] arr) {
        Heap heap = new Heap(arr.length);
        for (int i : arr) {
            heap.insert(i);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.extractMin();
        }
    }
}
