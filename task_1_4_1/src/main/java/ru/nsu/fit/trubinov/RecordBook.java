package ru.nsu.fit.trubinov;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Tests, Javadocs

@SuppressWarnings("unused")
public class RecordBook {
    public double averageMark(Student student) {
        double res = 0;
        for (Semester i : student.semesters) {
            res += i.averageMarkInSemester();
        }
        return res / student.semesters.size();
    }

    public boolean isScholarshipIncreased(Semester semester) {
        int cnt4 = 0;
        for (var i : semester.marks.keySet()) {
            if (semester.marks.get(i) == 4) {
                cnt4++;
            }
            if (cnt4 == 2 || semester.marks.get(i) < 4) {
                return false;
            }
        }
        return true;
    }

    public static class Student {
        public String name;
        public ArrayList<Semester> semesters = new ArrayList<>();

        public void addSemester(Semester semester) {
            semesters.add(semester);
        }
    }

    public static class Subject {
        public String subjectName;
        public String teacherName;
    }

    public static class Semester {
        public HashMap<Subject, Integer> marks = new HashMap<>();
        int semesterId;

        public void addSubject(Subject subject, int mark) {
            marks.put(subject, mark);
        }

        public double averageMarkInSemester() {
            double res = 0;
            for (Subject i : marks.keySet()) {
                res += marks.get(i);
            }
            return res / marks.size();
        }
    }
}

