package ru.nsu.fit.trubinov;

import static java.lang.Math.pow;

public class Number {
    public double real;
    public double imaginary;

    public Number(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public String toString() {
        if (real == 0 && imaginary == 0) {
            return "(0.0)";
        } else if (real == 0) {
            return "(i * (" + imaginary + "))";
        } else if (imaginary == 0) {
            return "(" + real + ")";
        }
        return "(" + real + " + " + "i * (" + imaginary + "))";
    }

    public boolean equals(Number a, Number b) {
        return a.real == b.real && a.imaginary == b.imaginary;
    }

    public Number add(Number a, Number b) {
        return new Number(a.real + b.real, a.imaginary + b.imaginary);
    }

    public Number sub(Number a, Number b) {
        return new Number(a.real - b.real, a.imaginary - b.imaginary);
    }

    public Number mul(Number a, Number b) {
        return new Number(a.real * b.real - a.imaginary * b.imaginary,
                a.real * b.imaginary + a.imaginary * b.real);
    }

    public Number div(Number a, Number b) {
        return new Number((a.real * b.real + a.imaginary * b.imaginary) / (pow(b.real, 2) + pow(b.imaginary, 2)),
                (a.imaginary * b.real + a.real * b.imaginary) / (pow(b.real, 2) + pow(b.imaginary, 2)));
    }
}
