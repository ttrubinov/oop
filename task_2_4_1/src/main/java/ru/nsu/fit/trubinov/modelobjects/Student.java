package ru.nsu.fit.trubinov.modelobjects;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String nickname;
    private String fullName;
    private String githubURL;
    private List<Task> tasks;
}
