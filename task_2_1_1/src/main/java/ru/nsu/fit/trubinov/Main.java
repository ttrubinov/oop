package ru.nsu.fit.trubinov;

public class Main {
    public static void main(String[] args) {
        Stack myStack = new Stack(100000);
        myStack.push(2);
        myStack.print();
        myStack.push(7);
        myStack.print();
        Stack stackToPush = new Stack(10);
        stackToPush.push(4);
        stackToPush.push(8);
        myStack.pushStack(stackToPush);
        myStack.print();
        myStack.pop();
        myStack.print();
        myStack.popStack(2);
        myStack.print();
        System.out.println(myStack.count());
    }
}