package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Aho-Corasick algorithm of finding all occurrences
 * of a substring in a stream by constructing a finite-state machine.
 * Reads characters from a stream one by one, but requires a state
 * for every possible symbol in an alphabet, so memory consumption
 * doesn't exceed (size of an alphabet) * (length of a substring).
 */
public class AhoCorasick implements SubstringFinderInStream {
    private static final int ALPHABET = 256;
    private BufferedReader reader;
    private char[] substring;
    private State[] states;

    /**
     * Find indices of all occurrences of an input substring
     * in an input stream by Aho-Corasick algorithm.
     * It constructs finite-state machine by a substring.
     *
     * @param input     input stream
     * @param substring substring to find in the stream
     * @return indices of all occurrences of the substring
     * @throws IOException if something went wrong with reading the stream
     */
    @Override
    public ArrayList<Integer> find(InputStream input, String substring) throws IOException {
        ArrayList<Integer> res = new ArrayList<>();
        init(input, substring.toCharArray());
        int v = 0;
        int cnt = 0;
        int a;
        for (char c = (char) (a = reader.read()); a != -1; c = (char) (a = reader.read())) {
            v = go(v, c);
            cnt++;
            if (states[v].isLeaf) {
                res.add(cnt - this.substring.length);
            }
        }
        return res;
    }

    private void init(InputStream input, char[] string) {
        substring = string;
        reader = new BufferedReader(new InputStreamReader(input));
        states = new State[substring.length + 1];
        for (int i = 0; i < states.length; i++) {
            states[i] = new State();
        }
        states[0].parent = states[0].link = -1;
        Arrays.fill(states[0].next, -1);
        Arrays.fill(states[0].go, -1);
        int v = 0;
        int sz = 1;
        for (char c : substring) {
            Arrays.fill(states[sz].next, -1);
            Arrays.fill(states[sz].go, -1);
            states[sz].link = -1;
            states[sz].parent = v;
            states[sz].parentChar = c;
            states[v].next[c] = sz++;
            v = states[v].next[c];
        }
        states[v].isLeaf = true;
    }

    private int getLink(int v) {
        if (states[v].link == -1) {
            if (states[v].parent == 0) {
                states[v].link = 0;
            } else {
                states[v].link = go(getLink(states[v].parent), states[v].parentChar);
            }
        }
        return states[v].link;
    }

    private int go(int v, char c) {
        if (states[v].go[c] == -1) {
            if (states[v].next[c] != -1) {
                states[v].go[c] = states[v].next[c];
            } else if (v == 0) {
                states[v].go[c] = 0;
            } else {
                states[v].go[c] = go(getLink(v), c);
            }
        }
        return states[v].go[c];
    }

    private static class State {
        int[] next = new int[ALPHABET];
        boolean isLeaf;
        int parent;
        char parentChar;
        int link;
        int[] go = new int[ALPHABET];
    }
}
