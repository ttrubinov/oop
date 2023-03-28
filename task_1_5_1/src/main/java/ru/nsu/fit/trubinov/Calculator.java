package ru.nsu.fit.trubinov;

import ru.nsu.fit.trubinov.functions.Function;
import ru.nsu.fit.trubinov.number.Number;
import ru.nsu.fit.trubinov.parser.Parser;

import java.util.Stack;

/**
 * Calculator class, which can calculate result of expression by parser of this expression.
 */
public class Calculator<P extends Parser<N>, N extends Number> {
    P parser;
    N number;

    public Calculator(P parser) {
        this.parser = parser;
    }

    Stack<Function<N>> stack = new Stack<>();

    /**
     * Calculation of the expression by functions storage in stack.
     *
     * @return result number
     */
    public Number calculate() {
        String token;
        while ((token = parser.getToken()) != null) {
            if (parser.isNumber(token)) {
                if (stack.empty() || stack.peek().getArity() == stack.peek().getArgs().size()) {
                    throw new IllegalArgumentException("Wrong input");
                }
                stack.peek().getArgs().add((parser.getNumber(token)));
                while (stack.peek().applicable()) {
                    number = stack.peek().apply();
                    stack.pop();
                    if (stack.empty()) {
                        return number;
                    }
                    stack.peek().getArgs().add(number);
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
}
