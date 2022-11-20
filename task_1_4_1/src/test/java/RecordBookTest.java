import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.RecordBook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecordBookTest {
    @Test
    void testRecordBook() {
        RecordBook a = new RecordBook();
        RecordBook.Student Petya = new RecordBook.Student();
        Petya.name = "Petya";
        RecordBook.Subject math = new RecordBook.Subject();
        math.subjectName = "Math";
        math.teacherName = "TeacherName";
        RecordBook.Subject history = new RecordBook.Subject();
        history.subjectName = "History";
        history.teacherName = "TeacherName";
        RecordBook.Subject physics = new RecordBook.Subject();
        physics.subjectName = "Physics";
        physics.teacherName = "TeacherName";
        RecordBook.Semester semester1 = new RecordBook.Semester();
        semester1.addSubject(math, 4);
        semester1.addSubject(history, 5);
        semester1.addSubject(physics, 5);
        Petya.addSemester(semester1);
        assertEquals(semester1.averageMarkInSemester(), 14. / 3);
        assertEquals(a.averageMark(Petya), 14. / 3);
        assertTrue(a.isScholarshipIncreased(semester1));
    }
}
