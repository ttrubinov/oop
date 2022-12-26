package ru.nsu.fit.trubinov;

import ru.nsu.fit.trubinov.functions.Function;
import ru.nsu.fit.trubinov.number.ComplexNumber;
import ru.nsu.fit.trubinov.number.Number;
import ru.nsu.fit.trubinov.parser.Parser;
import ru.nsu.fit.trubinov.parser.StringParser;

import java.util.Stack;

import static java.lang.Double.parseDouble;

/**
 * Calculator class, which can calculate result of expression by parser of this expression.
 */
public class Calculator {
    Parser parser;
    Number<ComplexNumber> number = new ComplexNumber();
    Stack<Function<ComplexNumber>> stack = new Stack<>();

    public Calculator(String s) {
        this.parser = new StringParser(s);
    }

    /**
     * Calculation of the expression by functions storage in stack.
     *
     * @return result number
     */
    public Number<ComplexNumber> calculate() {
        String token;
        while ((token = parser.getToken()) != null) {
            if (isDouble(token)) {
                if (stack.empty() || stack.peek().getArity() == stack.peek().getArgs().size()) {
                    throw new IllegalArgumentException("Wrong input");
                }
                stack.peek().getArgs().add(new ComplexNumber(parseDouble(token), 0));
                while (stack.peek().applicable()) {
                    number = stack.peek().apply();
                    stack.pop();
                    if (stack.empty()) {
                        return number;
                    }
                    stack.peek().getArgs().add((ComplexNumber) number);
                }
            } else {
                stack.push(parser.getFunction(token));
            }
        }
        if (!stack.empty()) {
            throw new IllegalArgumentException("Wrong input");
        }
        return null;
    }

    private boolean isDouble(String s) {
        try {
            parseDouble(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
