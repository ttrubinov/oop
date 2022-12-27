import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import ru.nsu.fit.trubinov.Commands;
import ru.nsu.fit.trubinov.Note;
import ru.nsu.fit.trubinov.Notebook;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.nsu.fit.trubinov.Notebook.file;

public class NotebookTest {
    @Test
    void notebookTest() throws IOException {
        new FileWriter(file, false).close();
        CommandLine commandLine = new CommandLine(new Commands());
        commandLine.execute("notebook", "-add", "Note1", "Aboba");
        commandLine.execute("notebook", "-add", "Note2", "Aboba2");
        commandLine.execute("notebook", "-rm", "Note1");
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Note> notes;
        try {
            notes = objectMapper.readValue(file, Notebook.type);
        }
        catch (Exception e) {
            notes = new ArrayList<>();
        }
        Note note = new Note("Note2", "Aboba2");
        note.date = notes.get(0).date;
        ArrayList<Note> myNotes = new ArrayList<>();
        myNotes.add(note);
        assertEquals(myNotes.toString(), notes.toString());
    }
}
