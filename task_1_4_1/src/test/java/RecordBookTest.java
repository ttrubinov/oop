import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.RecordBook;
import ru.nsu.fit.trubinov.Semester;
import ru.nsu.fit.trubinov.Subject;

import java.util.ArrayList;
import ru.nsu.fit.trubinov.Subject.*;

import static org.junit.jupiter.api.Assertions.*;

public class RecordBookTest {
    @Test
    void testRecordBook() {
        RecordBook a = new RecordBook("Petya");
        Subject math = new Subject("Math", "MathTeacherName", Mark.EXCELLENT, Type.EXAM);
        Subject history = new Subject("History", "HistoryTeacherName",
                Mark.FAILED, Type.TEST);
        Subject physics = new Subject("Physics", "PhysicsTeacherName",
                Mark.PASSED, Type.TEST);
        Semester semester1 = new Semester(new ArrayList<>(), 0);
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
        Semester semester2 = new Semester(new ArrayList<>(), 1);
        math = new Subject("Math", "MathTeacherName", Mark.EXCELLENT, Type.EXAM);
        semester2.addSubject(math);
        history = new Subject("History", "HistoryTeacherName", Mark.PASSED, Type.TEST);
        semester2.addSubject(history);
        physics = new Subject("Physics", "PhysicsTeacherName", Mark.PASSED, Type.TEST);
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
