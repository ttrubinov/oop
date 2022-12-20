package ru.nsu.fit.trubinov;

import ru.nsu.fit.trubinov.number.ComplexNumber;
import ru.nsu.fit.trubinov.number.Number;

import java.util.ArrayList;

/**
 * Possible function in calculator.
 */
public class Function {
    public String name;
    public int arity;
    ArrayList<ComplexNumber> args;

    public Function(String name) {
        this.name = name;
        this.arity = switch (name) {
            case "pow", "+", "-", "*", "/" -> 2;
            case "sin", "cos", "log", "sqrt" -> 1;
            default -> throw new IllegalArgumentException("Wrong function");
        };
        args = new ArrayList<>();
    }

    /**
     * Checks if the function can be applied to its arguments.
     *
     * @return true if the function can be applied to its arguments
     */
    public boolean applicable() {
        return arity == args.size();
    }

    /**
     * Applying function to its arguments.
     *
     * @return result number
     */
    public Number<ComplexNumber> apply() {
        return switch (name) {
            case "+" -> args.get(0).add(args.get(1));
            case "-" -> args.get(0).sub(args.get(1));
            case "*" -> args.get(0).mul(args.get(1));
            case "/" -> args.get(0).div(args.get(1));
            case "cos" -> args.get(0).cos();
            case "sin" -> args.get(0).sin();
            case "log" -> args.get(0).log();
            default -> throw new IllegalArgumentException("Wrong function");
        };
    }
}
