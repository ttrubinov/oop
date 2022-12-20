package ru.nsu.fit.trubinov;

import java.util.ArrayList;

import static java.lang.Integer.max;

/**
 * Record book class, which can calculate average mark in a semester and in all semesters,
 * can show will you have increased scholarship and red diploma.
 */
public class RecordBook {
    private final ArrayList<Semester> semesters;
    public String studentName;

    /**
     * Constructor of record book class, put your name to create your record book.
     *
     * @param name name of a student
     */
    public RecordBook(String name) {
        studentName = name;
        this.semesters = new ArrayList<>();
    }

    /**
     * Add a semester to the record book.
     *
     * @param semester semester to add into the record book.
     */
    public void addSemester(Semester semester) {
        semesters.add(semester);
    }

    /**
     * Calculates average mark for all semesters.
     *
     * @return average mark
     */
    public double averageMark() {
        double res = 0;
        for (Semester i : semesters) {
            res += i.averageMarkInSemester();
        }
        return res / semesters.size();
    }

    /**
     * Calculates will you have increased scholarship in this semester.
     *
     * @param semester semester, scholarship in which needs to be calculated
     * @return will you have increased scholarship
     */
    public boolean isScholarshipIncreased(Semester semester) {
        int cnt4 = 0;
        for (Subject i : semester.subjects) {
            if (i.mark.val == 4) {
                cnt4++;
            }
            if (cnt4 == 2 || i.mark.val < 4) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates will you get red diploma after graduating.
     *
     * @return will you get red diploma
     */
    public boolean isDiplomaRed() {
        int cnt4 = 0;
        int cnt5 = 0;
        for (Semester semester : semesters) {
            for (Subject subject : semester.subjects) {
                if (subject.mark.val == 3 || subject.mark.val == 2) {
                    return false;
                }
                if (subject.mark.val == 4) {
                    cnt4++;
                }
                if (subject.mark.val == 5) {
                    cnt5++;
                }
            }
        }
        return ((double) cnt4) / (cnt4 + cnt5) < 0.25;
    }

    /**
     * Converts the record book into human-readable string format.
     *
     * @return record book in string format
     */
    public String toString() {
        String tmp = "Record book of " + studentName + "\n";
        int maxSize = tmp.length();

        for (Semester semester : semesters) {
            for (Subject subject : semester.subjects) {
                tmp = subject.teacherName + " |  = " + subject.subjectName
                        + subject.mark.val + "\n";
                maxSize = max(maxSize, tmp.length());
            }
        }
        StringBuilder s = new StringBuilder("\n" + "=".repeat(maxSize + 3) + "\n");
        int sLen = (maxSize - ("Record book of " + studentName).length());
        int sLen1;
        if (sLen % 2 == 0) {
            sLen /= 2;
            sLen1 = sLen;
        } else {
            sLen /= 2;
            sLen1 = sLen + 1;
        }
        s.append("| ").append(" ".repeat(sLen)).append("Record book of ")
                .append(studentName).append(" ".repeat(sLen1)).append("|\n");
        s.append("|").append("=".repeat(maxSize + 1)).append("|\n");
        int cnt = 1;
        for (Semester semester : semesters) {
            sLen = (maxSize - ("Semester " + cnt).length());
            if (sLen % 2 == 0) {
                sLen /= 2;
                sLen1 = sLen;
            } else {
                sLen /= 2;
                sLen1 = sLen + 1;
            }
            if (cnt > 1) {
                s.append("|").append("=".repeat(maxSize + 1)).append("|\n");
            }
            s.append("| ").append(" ".repeat(sLen)).append("Semester ")
                    .append(cnt).append(" ".repeat(sLen1)).append("|\n");
            s.append("|").append("=".repeat(maxSize + 1)).append("|\n");
            for (Subject subject : semester.subjects) {
                sLen = (maxSize - (subject.teacherName + " |  = "
                        + subject.subjectName + subject.mark.val).length());
                if (sLen % 2 == 0) {
                    sLen /= 2;
                    sLen1 = sLen;
                } else {
                    sLen /= 2;
                    sLen1 = sLen + 1;
                }
                s.append("| ").append(" ".repeat(sLen)).append(subject.subjectName)
                        .append(" = ").append(subject.mark.val).append(" | ")
                        .append(subject.teacherName).append(" ".repeat(sLen1)).append("|\n");
            }
            cnt++;
        }
        s.append("=".repeat(maxSize + 3)).append("\n");
        return s.toString();
    }

    /**
     * Possible marks.
     */
    public enum Mark {
        UNSATISFACTORY(2), SATISFACTORY(3), GOOD(4), EXCELLENT(5), FAILED(2), PASSED(5);
        public final Integer val;

        Mark(Integer val) {
            this.val = val;
        }

        public static Mark getMark(String mark) {
            return switch (mark) {
                case "FAILED" -> FAILED;
                case "PASSED" -> PASSED;
                case "2" -> UNSATISFACTORY;
                case "3" -> SATISFACTORY;
                case "4" -> GOOD;
                case "5" -> EXCELLENT;
                default -> throw new IllegalArgumentException(mark);
            };
        }
    }

    /**
     * Subject class.
     *
     * @param subjectName name of the subject
     * @param teacherName name of a teacher of the subject
     */
    public record Subject(String subjectName, String teacherName, Mark mark) {
        public Subject(String a, String b, int aa) {
            this(a, b, Mark.getMark(String.valueOf(aa)));
        }

        public Subject(String a, String b, String aa) {
            this(a, b, Mark.getMark(aa));
        }
    }

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
                if (subject.mark != Mark.PASSED && subject.mark != Mark.FAILED) {
                    cnt++;
                }
            }
            return cnt;
        }

        public int getTestCount() {
            int cnt = 0;
            for (Subject subject : subjects) {
                if (subject.mark == Mark.PASSED || subject.mark == Mark.FAILED) {
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
                res += i.mark.val;
            }
            return res / subjects.size();
        }
    }
}

