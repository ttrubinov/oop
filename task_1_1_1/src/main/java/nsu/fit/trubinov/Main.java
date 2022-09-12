package nsu.fit.trubinov;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 100500, 6, 1, -3, 9, 33, 34, 100500, 0, -3, 5, 4, 3, 2, 1};
        Heap.heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}