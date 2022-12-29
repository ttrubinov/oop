package ru.nsu.fit.trubinov;

/**
 * Subject class.
 *
 * @param subjectName name of the subject
 * @param teacherName name of a teacher of the subject
 */
public record Subject(String subjectName, String teacherName, Mark mark, Type type) {
    /**
     * Possible marks.
     */
    public enum Mark {
        UNSATISFACTORY(2), SATISFACTORY(3), GOOD(4), EXCELLENT(5), FAILED(2), PASSED(5);
        public final Integer val;

        Mark(Integer val) {
            this.val = val;
        }
    }

    public enum Type {
        EXAM, TEST, DiffTEST;
    }
}