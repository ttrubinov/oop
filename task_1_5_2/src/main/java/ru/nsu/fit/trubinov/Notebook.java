package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Notebook {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String PATH = "";
    private static final String NAME = "data.txt";
    public static final File file = new File(PATH + NAME);
    public static final JavaType type = (new ObjectMapper()).getTypeFactory().
            constructCollectionType(ArrayList.class, Note.class);

    public void addNote(String name, String content) throws IOException {
        ArrayList<Note> notes;
        try {
            notes = objectMapper.readValue(file, type);
        }
        catch (Exception e) {
            notes = new ArrayList<>();
        }
        Note note = new Note(name, content);
        notes.add(note);
        objectMapper.writeValue(file, notes);
    }

    public void removeNote(String name) throws IOException {
        ArrayList<Note> notes;
        try {
            notes = objectMapper.readValue(file, type);
        }
        catch (Exception e) {
            notes = new ArrayList<>();
        }
        notes.removeIf(note -> note.name.equals(name));
        objectMapper.writeValue(file, notes);
    }

    public void printNotes() {
        ArrayList<Note> notes;
        try {
            notes = objectMapper.readValue(file, type);
        }
        catch (Exception e) {
            notes = new ArrayList<>();
        }
        System.out.println(notes);
    }
}
