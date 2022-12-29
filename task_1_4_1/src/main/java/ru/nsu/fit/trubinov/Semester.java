package ru.nsu.fit.trubinov;

import java.util.ArrayList;

/**
 * Semester class, which can calculate average mark in this semester.
 * You can add new subject to the semester.
 *
 * @param subjects   subjects
 * @param semesterId id of this semester.
 */
public record Semester(ArrayList<Subject> subjects, int semesterId) {

    public int getExamsCount() {
        int cnt = 0;
        for (Subject subject : subjects) {
            if (subject.mark() != Subject.Mark.PASSED && subject.mark() != Subject.Mark.FAILED) {
                cnt++;
            }
        }
        return cnt;
    }

    public int getTestCount() {
        int cnt = 0;
        for (Subject subject : subjects) {
            if (subject.mark() == Subject.Mark.PASSED || subject.mark() == Subject.Mark.FAILED) {
                cnt++;
            }
        }
        return cnt;
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public double averageMarkInSemester() {
        double res = 0;
        for (Subject i : subjects) {
            res += i.mark().val;
        }
        return res / subjects.size();
    }

    public ArrayList<Subject> subjects() {
        return subjects;
    }

    public int semesterId() {
        return semesterId;
    }
}
