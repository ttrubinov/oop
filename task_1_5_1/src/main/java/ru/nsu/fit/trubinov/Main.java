package ru.nsu.fit.trubinov;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator("* 58 + 7 3");
        System.out.println(calculator.calculate());
    }
}
