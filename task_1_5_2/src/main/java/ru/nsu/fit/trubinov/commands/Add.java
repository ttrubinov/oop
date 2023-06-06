package ru.nsu.fit.trubinov.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import ru.nsu.fit.trubinov.Notebook;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "add", description = "add a note")
public class Add implements Callable<Integer> {
    @Parameters(index = "0")
    String noteName;

    @Parameters(index = "1")
    String noteText;

    @Override
    public Integer call() throws IOException {
        Notebook notebook = new Notebook(0);
        notebook.addNote(noteName, noteText);
        return null;
    }
}
