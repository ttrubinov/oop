package ru.nsu.fit.trubinov.parser;

public class StringParser implements Parser {
    String input;
    String[] inputArr;
    int cnt = 0;

    public StringParser(String input) {
        this.input = input;
        this.inputArr = input.split("\\s+");
    }

    public String getToken() {
        if (cnt >= inputArr.length) {
            return null;
        }
        return inputArr[cnt++];
    }

}
