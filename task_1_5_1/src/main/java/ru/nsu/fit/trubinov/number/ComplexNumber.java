package ru.nsu.fit.trubinov.number;

import static java.lang.Math.atan;

/**
 * Class that implements complex number.
 */
public class ComplexNumber implements Number<ComplexNumber> {
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

    /**
     * Checks if this number equals to another.
     *
     * @param b another number
     * @return true if numbers is equal
     */
    public boolean equals(ComplexNumber b) {
        return this.real == b.real && this.imaginary == b.imaginary;
    }

    /**
     * Addition of numbers.
     *
     * @param b number to add
     * @return result number
     */
    public ComplexNumber add(ComplexNumber b) {
        return new ComplexNumber(this.real + b.real, this.imaginary + b.imaginary);
    }

    /**
     * Subtraction of numbers.
     *
     * @param b number to add
     * @return result number
     */
    public ComplexNumber sub(ComplexNumber b) {
        return new ComplexNumber(this.real - b.real, this.imaginary - b.imaginary);
    }

    /**
     * Multiplication of numbers.
     *
     * @param b number to add
     * @return result number
     */
    public ComplexNumber mul(ComplexNumber b) {
        return new ComplexNumber(this.real * b.real - this.imaginary * b.imaginary,
                this.real * b.imaginary + this.imaginary * b.real);
    }

    /**
     * Division of numbers.
     *
     * @param b number to add
     * @return result number
     */
    public ComplexNumber div(ComplexNumber b) {
        return new ComplexNumber((this.real * b.real + this.imaginary * b.imaginary) / (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)),
                (this.imaginary * b.real + this.real * b.imaginary) / (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)));
    }

    /**
     * Logarithm of number.
     *
     * @return result number
     */
    public ComplexNumber log() {
        return new ComplexNumber(ln(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2)), atan(this.real / this.imaginary));
    }

    private double ln(double a) {
        return Math.log(a);
    }

    /**
     * Cosine of number.
     *
     * @return result number
     */
    public ComplexNumber cos() {
        return new ComplexNumber(Math.cos(this.real) * Math.cosh(this.imaginary),
                -Math.sin(this.real) * Math.sinh(this.imaginary));
    }

    /**
     * Sinus of number.
     *
     * @return result number
     */
    public ComplexNumber sin() {
        return new ComplexNumber(Math.sin(this.real) * Math.cosh(this.imaginary),
                Math.cos(this.real) * Math.sinh(this.imaginary));
    }
}
