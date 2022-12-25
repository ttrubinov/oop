package ru.nsu.fit.trubinov;

import java.util.ArrayList;

public class Notebook {
    private final ArrayList<Note> notes = new ArrayList<>();

    public void addNote(String name, String content) {
        Note note = new Note(name, content);
        notes.add(note);
    }

    public void removeNote(String name) {
        notes.removeIf(note -> note.name.equals(name));
    }

    public void printNotes() {
        System.out.println(notes);
    }
}
