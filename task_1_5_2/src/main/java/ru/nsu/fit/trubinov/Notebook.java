package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Notebook class, that can apply operations to notes.
 * It saves data in json format in file.
 */
public class Notebook {
    public Integer id;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String PATH = "";
    private final String NAME = id + "data.json";
    public final File file = new File(PATH + NAME);
    public static final JavaType type = (new ObjectMapper()).getTypeFactory().
            constructCollectionType(ArrayList.class, Note.class);

    public Notebook(Integer id) {
        this.id = id;
    }

    /**
     * Add a note to the notebook.
     *
     * @param name name of a file
     * @param content content of a file
     * @throws IOException file is broken
     */
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

    /**
     * Remove a note from the notebook.
     * Does nothing if there is no note with such name.
     *
     * @param name name of a note
     * @throws IOException file is broken
     */
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

    /**
     * Print all the notes of the notebook, sorted by date of creation.
     */
    public void printNotes() {
        ArrayList<Note> notes;
        try {
            notes = objectMapper.readValue(file, type);
        }
        catch (Exception e) {
            notes = new ArrayList<>();
        }
        notes.sort(Comparator.comparing(o -> o.date));
        System.out.println(notes);
    }
}
