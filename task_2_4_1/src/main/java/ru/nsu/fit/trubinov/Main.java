package ru.nsu.fit.trubinov;

import picocli.CommandLine;
import ru.nsu.fit.trubinov.сommands.Commands;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Commands()).execute(args);
        System.exit(exitCode);
    }
}