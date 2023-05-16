package ru.nsu.fit.trubinov.modelobjects;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String name;
    private List<Student> students;
}
