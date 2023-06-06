package ru.nsu.fit.trubinov.commands;

import picocli.CommandLine.*;
import ru.nsu.fit.trubinov.Notebook;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "Show", description = "show all notes")
public class Show implements Callable<Integer> {
    @Option(names = "from", arity = "1", description = "time from which notes should be shown")
    Date from;

    @Option(names = "by", arity = "1", description = "time by which notes should be shown")
    Date by;

    @Parameters(arity = "0..*")
    List<String> keyWords;

    @Override
    public Integer call() {
        Notebook notebook = new Notebook(0);
        notebook.printNotes(from, by, keyWords);
        return null;
    }
}
