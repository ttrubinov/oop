package ru.nsu.fit.trubinov.parser;

/**
 * Parsing expression from string.
 */
public class StringParser implements Parser {
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

}
