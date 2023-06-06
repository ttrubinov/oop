package ru.nsu.fit.trubinov;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Single note, that has name, content and date of creation.
 */
public class Note {
    @JsonSerialize
    public String name;

    @JsonSerialize
    public String content;

    @JsonSerialize
    public Date date;


    public Note(String name, String content) {
        this.name = name;
        this.content = content;
        this.date = new Date();
    }

    /**
     * Constructor for parsing from json file.
     *
     * @param name name of a note
     * @param content content of a note
     * @param date date of creation of a note
     */
    @JsonCreator
    public Note(@JsonProperty("name") String name,
                @JsonProperty("content") String content,
                @JsonProperty("date") Date date) {
        this.name = name;
        this.content = content;
        this.date = date;
    }

    @Override
    public String toString() {
        return "\n'" + name + "' " + "(" + date + ")\n" + "'" + content + "'\n";
    }
}
