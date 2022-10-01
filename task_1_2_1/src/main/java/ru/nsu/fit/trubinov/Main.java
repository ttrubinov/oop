package ru.nsu.fit.trubinov;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> myStack = new Stack<>();
        myStack.push(2);
        System.out.print("push(2): ");
        myStack.print();
        myStack.push(7);
        System.out.print("push(7): ");
        myStack.print();
        Stack<Integer> stackToPush = new Stack<>();
        stackToPush.push(4);
        stackToPush.push(8);
        System.out.print("stackToPush: ");
        stackToPush.print();
        myStack.pushStack(stackToPush);
        System.out.print("pushStack: ");
        myStack.print();
        System.out.println("popped elem: " + myStack.pop());
        System.out.print("pop(): ");
        myStack.print();
        System.out.print("poppedStack: ");
        Stack<Integer> poppedStack = myStack.popStack(2);
        poppedStack.print();
        System.out.print("popStack(2): ");
        myStack.print();
        System.out.print("cnt: ");
        System.out.println(myStack.count());
    }
}