package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Knuth-Morris-Pratt algorithm of finding all occurrences
 * of a substring in a stream, using prefix-function.
 * Symbol '#' cannot be used in a stream, because
 * this algorithm requires to add one character to the end
 * of a substring, which will never be used in the main string.
 * Reads characters from a stream one by one, so memory consumption
 * doesn't exceed 2 * length of a substring.
 */
public class KnuthMorrisPratt implements SubstringFinderInStream {
    private BufferedReader reader;
    private char[] s;
    private ArrayList<Integer> prefix;

    /**
     * Find indices of all occurrences of an input substring
     * in an input stream by Knuth-Morris-Pratt algorithm.
     *
     * @param input     input stream
     * @param substring substring to find in the stream
     * @return indices of all occurrences of the substring
     * @throws IOException if something went wrong with reading the stream
     */
    @Override
    public ArrayList<Integer> find(InputStream input, String substring) throws IOException {
        init(input, substring.toCharArray());
        ArrayList<Integer> res = new ArrayList<>();
        int a;
        int pos = s.length;
        int fileLen = 0;
        for (char c = (char) (a = reader.read()); a != -1; c = (char) (a = reader.read())) {
            prefixFunction(c, pos);
            if (prefix.get(pos) == s.length - 1) {
                res.add(fileLen - s.length + 2);
            }
            if (prefix.get(pos) == 0 && prefix.get(pos - 1) == 0) {
                Iterator<Integer> iterator = prefix.listIterator(s.length);
                for (int i = s.length; i <= pos; i++) {
                    iterator.next();              // deleting useless prefixes
                    iterator.remove();            // to reduce memory consumption
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
            prefixFunction(s[i], i);
        }
    }

    private void prefixFunction(char c, int i) {
        int j = prefix.get(i - 1);
        while (j > 0 && c != s[j]) {
            j = prefix.get(j - 1);
        }
        if (c == s[j]) {
            j++;
        }
        prefix.add(j);
    }
}
