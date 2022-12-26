package ru.nsu.fit.trubinov.functions;

import ru.nsu.fit.trubinov.number.ComplexNumber;

import java.util.ArrayList;
import java.util.List;

public class Divide implements Function<ComplexNumber> {
    int arity = 2;
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
        ComplexNumber b = args.get(1);
        return new ComplexNumber((a.real * b.real + a.imaginary * b.imaginary) /
                        (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)),
                (a.imaginary * b.real + a.real * b.imaginary) /
                        (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)));
    }
}
