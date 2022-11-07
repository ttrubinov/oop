package ru.nsu.fit.trubinov;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\TTrubinov\\IdeaProjects\\oop\\task_1_3_1\\src\\main\\java\\ru\\nsu\\fit\\trubinov\\input.txt");
        Substring s = new Substring(f, "ag");
        s.SubstringFinder();
    }
}