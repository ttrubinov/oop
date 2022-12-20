package ru.nsu.fit.trubinov.number;

public class MyNumber implements Number<MyNumber> {
    public double real;
    public double imaginary;

    public MyNumber() {
    }


    public MyNumber(double real, double imaginary) {
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

    public boolean equals(MyNumber b) {
        return this.real == b.real && this.imaginary == b.imaginary;
    }

    public MyNumber add(MyNumber b) {
        return new MyNumber(this.real + b.real, this.imaginary + b.imaginary);
    }

    public MyNumber sub(MyNumber b) {
        return new MyNumber(this.real - b.real, this.imaginary - b.imaginary);
    }

    public MyNumber mul(MyNumber b) {
        return new MyNumber(this.real * b.real - this.imaginary * b.imaginary,
                this.real * b.imaginary + this.imaginary * b.real);
    }

    public MyNumber div(MyNumber b) {
        return new MyNumber((this.real * b.real + this.imaginary * b.imaginary) / (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)),
                (this.imaginary * b.real + this.real * b.imaginary) / (Math.pow(b.real, 2) + Math.pow(b.imaginary, 2)));
    }

    public MyNumber pow(MyNumber b) {
        return new MyNumber();
    }

    public MyNumber log() {
        return new MyNumber();
    }

    public MyNumber cos() {
        return new MyNumber();
    }

    public MyNumber sin() {
        return new MyNumber();
    }

    public MyNumber sqrt() {
        return new MyNumber();
    }
}
