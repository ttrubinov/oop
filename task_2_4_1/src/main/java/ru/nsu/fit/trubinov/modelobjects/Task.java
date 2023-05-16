package ru.nsu.fit.trubinov.modelobjects;

import lombok.Data;

@Data
public class Task {
    private String id;
    private String name;
    private int softDeadline;
    private int hardDeadline;
    private boolean isSubmitted;
    private int submittedDate;
    private int score;
}
