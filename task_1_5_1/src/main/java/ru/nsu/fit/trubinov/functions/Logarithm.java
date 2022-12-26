package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.atan;

public class Logarithm implements Function<ComplexNumber> {
    int arity = 1;
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
     * Get argument of logarithm.
     *
     * @return list of arguments
     */
    @Override
    public List<ComplexNumber> getArgs() {
        return args;
    }

    /**
     * Logarithm calculating.
     *
     * @return result of logarithm
     */
    @Override
    public ComplexNumber apply() {
        ComplexNumber a = args.get(0);
        return new ComplexNumber(ln(Math.pow(a.real, 2) + Math.pow(a.imaginary, 2)), atan(a.real / a.imaginary));
    }

    private double ln(double a) {
        return Math.log(a);
    }
}
