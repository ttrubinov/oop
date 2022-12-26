package ru.nsu.fit.trubinov;

import ru.nsu.fit.trubinov.RecordBook.*;

/**
 * Subject class.
 *
 * @param subjectName name of the subject
 * @param teacherName name of a teacher of the subject
 */
public record Subject(String subjectName, String teacherName, Mark mark, Type type) {

    @Override
    public String subjectName() {
        return subjectName;
    }

    @Override
    public String teacherName() {
        return teacherName;
    }

    @Override
    public Mark mark() {
        return mark;
    }

    @Override
    public Type type() {
        return type;
    }
}