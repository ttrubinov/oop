package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


@SuppressWarnings("unused")
public class Substring {
    public File f;
    public String s;

    public Substring(File file, String string) {
        f = file;
        s = string;
    }

    public ArrayList<Integer> SubstringFinder() throws IOException {
        char[] input = new char[100500];
        int offset = 0;
        BufferedReader reader = new BufferedReader(new FileReader(f));
        while (true) {
            try {
                int a = reader.read(input, offset++, 100500);
            } catch (Exception c) {
                input[offset] = '\0';
                break;
            }
        }
        for (int i = 0; i < 100500; i++) {
            if (input[i] == '\0') {
                break;
            }
            System.out.print(input[i]);
        }
        return null;
    }
}
