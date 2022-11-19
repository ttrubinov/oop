package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class KnuthMorrisPratt implements SubstringFinderInStream {
    private BufferedReader reader;
    private char[] s;
    private ArrayList<Integer> prefix;

    @Override
    public ArrayList<Integer> find(InputStream input, char[] substring) throws IOException {
        init(input, substring);
        ArrayList<Integer> res = new ArrayList<>();
        int a;
        int pos = s.length;
        int fileLen = 0;
        for (char c = (char) (a = reader.read()); a != -1; c = (char) (a = reader.read())) {
            prefix_function(c, pos);
            if (prefix.get(pos) == s.length - 1) {
                res.add(fileLen - s.length + 2);
            }
            if (prefix.get(pos) == 0 && prefix.get(pos - 1) == 0) {
                Iterator<Integer> iterator = prefix.listIterator(s.length);
                for (int i = s.length; i <= pos; i++) {
                    iterator.next();
                    iterator.remove();
                }
                pos = s.length - 1;
            }
            pos++;
            fileLen++;
        }
        return res;
    }

    private void init(InputStream input, char[] string) {
        s = new char[string.length + 1];
        System.arraycopy(string, 0, s, 0, string.length);
        s[string.length] = '#';
        reader = new BufferedReader(new InputStreamReader(input));
        prefix = new ArrayList<>();
        prefix.add(0);
        for (int i = 1; i < s.length; i++) {
            prefix_function(s[i], i);
        }
    }

    private void prefix_function(char c, int i) {
        int j = prefix.get(i - 1);
        while (j > 0 && c != s[j])
            j = prefix.get(j - 1);
        if (c == s[j]) j++;
        prefix.add(j);
    }
}
