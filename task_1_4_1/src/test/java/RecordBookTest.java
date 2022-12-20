import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.RecordBook;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RecordBookTest {
    @Test
    void testRecordBook() {
        RecordBook a = new RecordBook("Petya");
        RecordBook.Subject math = new RecordBook.Subject("Math", "MathTeacherName", 5);
        RecordBook.Subject history = new RecordBook.Subject("History", "HistoryTeacherName", "FAILED");
        RecordBook.Subject physics = new RecordBook.Subject("Physics", "PhysicsTeacherName", "PASSED");
        RecordBook.Semester semester1 = new RecordBook.Semester(new ArrayList<>(), 0);
        semester1.addSubject(math);
        semester1.addSubject(history);
        semester1.addSubject(physics);
        a.addSemester(semester1);
        assertEquals(semester1.averageMarkInSemester(), 12. / 3);
        assertEquals(a.averageMark(), 12. / 3);
        assertFalse(a.isScholarshipIncreased(semester1));
        assertFalse(a.isDiplomaRed());
        assertEquals(1, semester1.getExamsCount());
        assertEquals(2, semester1.getTestCount());
        RecordBook.Semester semester2 = new RecordBook.Semester(new ArrayList<>(), 1);
        math = new RecordBook.Subject("Math", "MathTeacherName", 5);
        semester2.addSubject(math);
        history = new RecordBook.Subject("History", "HistoryTeacherName", "PASSED");
        semester2.addSubject(history);
        physics = new RecordBook.Subject("Physics", "PhysicsTeacherName", "PASSED");
        semester2.addSubject(physics);
        a.addSemester(semester2);
        assertEquals(semester2.averageMarkInSemester(), 5);
        assertEquals(a.averageMark(), 4.5);
        assertTrue(a.isScholarshipIncreased(semester2));
        assertFalse(a.isDiplomaRed());
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
