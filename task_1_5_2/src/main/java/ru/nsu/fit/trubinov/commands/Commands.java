package ru.nsu.fit.trubinov.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

/**
 * Commands that can be used as console arguments.
 */
@Command(name = "notebook",
        subcommands = {Add.class, Remove.class, Show.class},
        description = "action with notes")
public class Commands implements Callable<Integer> {
    /**
     * Run commands coming from user console.
     *
     * @return result code of the call
     */
    @Override
    public Integer call() {
        return null;
    }
}