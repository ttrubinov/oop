package ru.nsu.fit.trubinov;

import java.util.Date;

public class Note {
    String name;
    String content;
    Date date;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
        this.date = new Date();
    }
}
