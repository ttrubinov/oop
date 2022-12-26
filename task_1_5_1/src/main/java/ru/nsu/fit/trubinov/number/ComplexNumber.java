package ru.nsu.fit.trubinov.number;

/**
 * Class that implements complex number.
 */
public class ComplexNumber implements Number {
    public double real;
    public double imaginary;

    public ComplexNumber() {
    }


    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * String representation of the number.
     *
     * @return string
     */
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
}
