package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Substring {
    public File f;
    public String s;

    public Substring(File file, String string) {
        f = file;
        s = string;
    }

    public ArrayList<Integer> SubstringFinder() throws IOException {
        long hash_s = 0;
        for (int i = 0; i < s.length(); i++)
            hash_s += s.charAt(i);
        long hash_f = 0;
        int i = 0;
        ArrayList<Integer> res = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(f));
        List<Character> buf = new LinkedList<>();
        int a = reader.read();
        do {
            if (i >= s.length()) {
                char c = buf.remove(0);
                hash_f -= c;
            }
            buf.add((char) a);
            hash_f += a;
            if (hash_s == hash_f && s.length() == buf.size()) {
                int flag = 0;
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j) != buf.get(j)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    res.add(i - s.length() + 1);
                }
            }
            i++;
            a = reader.read();
        } while (a != -1);
        return res;
    }
}