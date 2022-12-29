package ru.nsu.fit.trubinov.commands;

import picocli.CommandLine.*;
import ru.nsu.fit.trubinov.Notebook;

import java.util.concurrent.Callable;

@Command(name = "Show", description = "show all notes")
public class Show implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Notebook notebook = new Notebook(0);
        notebook.printNotes();
        return null;
    }
}
