package ru.nsu.fit.trubinov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the length of the array");
        int len = scan.nextInt();
        if (len < 0) {
            throw new IllegalArgumentException();
        }
        int[] arr = new int[len];
        System.out.println("Enter the array: ");
        for (int i = 0; i < len; i++) {
            arr[i] = scan.nextInt();
        }
        Heap.heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}