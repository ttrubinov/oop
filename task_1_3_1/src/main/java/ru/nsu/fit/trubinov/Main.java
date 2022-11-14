package ru.nsu.fit.trubinov;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("./task_1_3_1/src/main/java/ru/nsu/fit/trubinov/input.txt");
        InputStream stream = new FileInputStream(f);
        SubstringFinderInStream s = new Aho_Corasick();
        System.out.println(s.find(stream, ("aaaaaaaaaaaaaaaaaa").toCharArray()));
    }
}