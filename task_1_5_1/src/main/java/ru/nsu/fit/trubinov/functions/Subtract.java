package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

public class Subtract implements Function<ComplexNumber> {
    int arity = 2;
    List<ComplexNumber> args = new ArrayList<>();

    /**
     * Get arity of the function.
     *
     * @return arity
     */
    @Override
    public int getArity() {
        return arity;
    }

    /**
     * Get arguments that should be subtracted.
     *
     * @return list of arguments
     */
    @Override
    public List<ComplexNumber> getArgs() {
        return args;
    }

    /**
     * Subtraction of arguments.
     *
     * @return result of the subtraction
     */
    @Override
    public ComplexNumber apply() {
        ComplexNumber a = args.get(0);
        ComplexNumber b = args.get(1);
        return new ComplexNumber(a.real - b.real,
                a.imaginary - b.imaginary);
    }
}
