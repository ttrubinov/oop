package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

public class Sinus implements Function<ComplexNumber> {
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
     * Get arguments of sinus.
     *
     * @return list of arguments
     */
    @Override
    public List<ComplexNumber> getArgs() {
        return args;
    }

    /**
     * Sinus calculating.
     *
     * @return result of sinus
     */
    @Override
    public ComplexNumber apply() {
        ComplexNumber a = args.get(0);
        return new ComplexNumber(Math.sin(a.real) * Math.cosh(a.imaginary),
                Math.cos(a.real) * Math.sinh(a.imaginary));
    }
}
