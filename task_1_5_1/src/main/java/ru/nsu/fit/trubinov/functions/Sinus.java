package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

public class Sinus implements Function<ComplexNumber> {
    int arity = 1;
    List<ComplexNumber> args = new ArrayList<>();

    @Override
    public int getArity() {
        return arity;
    }

    @Override
    public List<ComplexNumber> getArgs() {
        return args;
    }

    @Override
    public ComplexNumber apply() {
        ComplexNumber a = args.get(0);
        return new ComplexNumber(Math.sin(a.real) * Math.cosh(a.imaginary),
                Math.cos(a.real) * Math.sinh(a.imaginary));
    }
}
