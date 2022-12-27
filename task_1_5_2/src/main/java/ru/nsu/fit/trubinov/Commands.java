package ru.nsu.fit.trubinov;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Commands that can be used as console arguments.
 */
@Command(name = "notebook", description = "action with notes")
public class Commands implements Callable<Integer> {
    @Option(names = "-add", arity = "2", description = "add a note")
    private List<String> addNote;

    @Option(names = "-rm", arity = "1", description = "delete a note")
    private String removeNote;

    @Option(names = "-show", arity = "0", description = "show all notes")
    private boolean show;

    @Parameters(index = "0..*")
    private List<String> parameters;

    /**
     * Run commands coming from user console.
     *
     * @return result code of the call
     * @throws IOException wrong input
     */
    @Override
    public Integer call() throws IOException {
        Notebook notebook = new Notebook();
        if (addNote != null) {
            notebook.addNote(addNote.get(0), addNote.get(1));
        }
        if (removeNote != null) {
            notebook.removeNote(removeNote);
        }
        if (show) {
            notebook.printNotes();
        }
        return null;
    }
}