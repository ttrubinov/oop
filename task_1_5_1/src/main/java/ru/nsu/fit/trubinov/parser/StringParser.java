package ru.nsu.fit.trubinov.parser;

import ru.nsu.fit.trubinov.functions.*;
import ru.nsu.fit.trubinov.number.ComplexNumber;

import static java.lang.Double.parseDouble;

/**
 * Parsing expression from string.
 */
public class StringParser implements Parser<ComplexNumber> {
    String input;
    String[] inputArr;
    int cnt = 0;

    public StringParser(String input) {
        this.input = input;
        this.inputArr = input.split("\\s+");
    }

    /**
     * Get next token in string.
     *
     * @return next token
     */
    public String getToken() {
        if (cnt >= inputArr.length) {
            return null;
        }
        return inputArr[cnt++];
    }

    public ComplexNumber getNumber(String s) {
        String[] complex = s.split(" ");
        return new ComplexNumber(parseDouble(complex[0]), 0);
    }

    public boolean isNumber(String s) {
        try {
            parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Function<ComplexNumber> getFunction(String token) {
        return switch (token) {
            case "+" -> new Add();
            case "-" -> new Subtract();
            case "*" -> new Multiply();
            case "/" -> new Divide();
            case "log" -> new Logarithm();
            case "sin" -> new Sinus();
            case "cos" -> new Cosine();
            default -> throw new IllegalArgumentException();
        };
    }
}
