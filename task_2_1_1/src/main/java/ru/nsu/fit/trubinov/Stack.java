package ru.nsu.fit.trubinov;

import java.util.EmptyStackException;

public class Stack {
    private int[] stack;
    private int cur;

    public Stack(int size) {
        stack = new int[size];
        cur = 0;
    }

    private boolean isEmpty() {
        if (cur == 0) {
            return true;
        }
        return false;
    }

    public void push(int elem) {
        stack[cur++] = elem;
    }

    public void pushStack(Stack st) {
        for (int i = 0; i < st.cur; i++) {
            push(st.stack[i]);
        }
    }

    public int pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            cur--;
            return stack[cur + 1];
        }
    }

    public Stack popStack(int cnt) {
        Stack st = new Stack(100000);
        for (int i = 0; i < cnt; i++) {
            st.stack[st.cur++] = pop();
        }
        return st;
    }

    public int count() {
        return cur;
    }

    public void print() {
        for (int i = 0; i < cur; i++) {
            System.out.print(stack[i] + " ");
        }
        System.out.println();
    }
}
