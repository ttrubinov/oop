package ru.nsu.fit.trubinov;

import ru.nsu.fit.trubinov.number.MyNumber;
import ru.nsu.fit.trubinov.number.Number;

import java.util.ArrayList;

public class Function {
    public String name;
    public int arity;
    ArrayList<MyNumber> args;

    public Function(String name) {
        this.name = name;
        this.arity = switch (name) {
            case "pow", "+", "-", "*", "/" -> 2;
            case "sin", "cos", "log", "sqrt" -> 1;
            default -> throw new IllegalArgumentException("Wrong function");
        };
        args = new ArrayList<>();
    }

    public boolean applicable() {
        return arity == args.size();
    }

    public Number<MyNumber> apply() {
        return switch (name) {
            case "+" -> args.get(0).add(args.get(1));
            case "-" -> args.get(0).sub(args.get(1));
            case "*" -> args.get(0).mul(args.get(1));
            case "/" -> args.get(0).div(args.get(1));
            case "pow" -> args.get(0).pow(args.get(1));
            case "cos" -> args.get(0).cos();
            case "sin" -> args.get(0).sin();
            case "log" -> args.get(0).log();
            case "sqrt" -> args.get(0).sqrt();
            default -> throw new IllegalArgumentException("Wrong function");
        };
    }
}
