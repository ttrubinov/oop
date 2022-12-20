package ru.nsu.fit.trubinov.parser;

import java.util.Scanner;

/**
 * Parsing expression from input.
 */
public class InputParser implements Parser {
    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine();
    String[] inputArr = input.split("\\s+");
    int cnt = 0;

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

}
