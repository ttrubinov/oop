package ru.nsu.fit.trubinov;

import java.util.Arrays;
import java.util.EmptyStackException;
/**
 * Stack with reallocating.
 *
 * @param <T> data type of elements in the stack
 * @author Timofey Trubinov
 * @version 0.2
 */
public class Stack<T> {
    private T[] stack;
    private int cur;
    private int cap;

    /**
     * Initializing current position, capacity and array.
     */
    @SuppressWarnings("unchecked")
    public Stack() {
        cur = 0;
        cap = 10;
        stack = (T[]) new Object[cap];
    }

    private void stackRealloc(Stack<T> st, int len) {
        st.stack = Arrays.copyOf(st.stack, st.cur * 2 + len);
        st.cap = st.cur * 2;
    }

    /**
     * Pushing an element to the stack.
     *
     * @param elem element to push
     */
    public void push(T elem) {
        if (cur >= cap) {
            stackRealloc(this, 0);
        }
        stack[cur++] = elem;
    }

    /**
     * Pushing one stack to the end of another.
     *
     * @param st stack to push
     * @throws NullPointerException if stack to push is empty
     */
    public void pushStack(Stack<T> st) {
        if (st == null) {
            throw new NullPointerException();
        }
        if (cur + st.cur >= cap) {
            stackRealloc(this, st.cur);
        }
        System.arraycopy(st.stack, 0, stack, cur, st.cur);
        cur += st.cur;
    }

    /**
     * Taking element from the end of the stack.
     *
     * @return popped element
     * @throws EmptyStackException if the stack is empty
     */
    public T pop() {
        if (cur == 0) {
            throw new EmptyStackException();
        }
        cur--;
        return stack[cur];
    }

    /**
     * Taking elements from the end of the stack and pushing them to another stack.
     *
     * @param cnt count of elements to pop
     * @return popped stack
     * @throws IllegalArgumentException if number of requested elements less than 0
     * @throws EmptyStackException      if number of requested elements more than number of stack elements
     */
    public Stack<T> popStack(int cnt) {
        if (cnt < 0) {
            throw new IllegalArgumentException();
        }
        if (cur < cnt) {
            throw new EmptyStackException();
        }
        Stack<T> st = new Stack<>();
        stackRealloc(st, cnt);
        System.arraycopy(stack, cur - cnt, st.stack, 0, cnt);
        cur -= cnt;
        st.cur = cnt;
        return st;
    }

    /**
     * Counting number of elements in the stack.
     *
     * @return count of elements in the stack
     */
    public int count() {
        return cur;
    }

    /**
     * Printing stack elements on the screen.
     */
    public void print() {
        if (cur == 0) {
            System.out.println("The stack is empty");
        } else {
            for (int i = 0; i < cur; i++) {
                System.out.print(stack[i] + " ");
            }
            System.out.println();
        }
    }
}
