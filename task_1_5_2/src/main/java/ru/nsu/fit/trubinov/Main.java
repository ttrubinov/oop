package ru.nsu.fit.trubinov;

import picocli.CommandLine;

/**
 * Processing user's commands.
 */
public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Commands()).execute(args);
        System.exit(exitCode);
    }
}
