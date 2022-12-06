import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.RecordBook;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RecordBookTest {
    @Test
    void testRecordBook() {
        RecordBook a = new RecordBook("Petya");
        RecordBook.Subject math = new RecordBook.Subject("Math", "MathTeacherName");
        RecordBook.Subject history = new RecordBook.Subject("History", "HistoryTeacherName");
        RecordBook.Subject physics = new RecordBook.Subject("Physics", "PhysicsTeacherName");
        RecordBook.Semester semester1 = new RecordBook.Semester(new HashMap<>(), 0);
        semester1.addSubject(math, 4);
        semester1.addSubject(history, 5);
        semester1.addSubject(physics, 5);
        a.addSemester(semester1);
        assertEquals(semester1.averageMarkInSemester(), 14. / 3);
        assertEquals(a.averageMark(), 14. / 3);
        assertTrue(a.isScholarshipIncreased(semester1));
        assertFalse(a.isDiplomaRed());
        RecordBook.Semester semester2 = new RecordBook.Semester(new HashMap<>(), 1);
        semester2.addSubject(math, 5);
        semester2.addSubject(history, 5);
        semester2.addSubject(physics, 5);
        a.addSemester(semester2);
        assertEquals(semester2.averageMarkInSemester(), 5);
        assertEquals(a.averageMark(), 4.833333333333334);
        assertTrue(a.isScholarshipIncreased(semester2));
        assertTrue(a.isDiplomaRed());
        System.out.println(a);
        // ====================================
        // |       Record book of Petya       |
        // |==================================|
        // |            Semester 1            |
        // |==================================|
        // | Physics = 5 | PhysicsTeacherName |
        // |    Math = 4 | MathTeacherName    |
        // | History = 5 | HistoryTeacherName |
        // |==================================|
        // |            Semester 2            |
        // |==================================|
        // | Physics = 5 | PhysicsTeacherName |
        // |    Math = 5 | MathTeacherName    |
        // | History = 5 | HistoryTeacherName |
        // ====================================
    }
}
