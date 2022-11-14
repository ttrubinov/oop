package ru.nsu.fit.trubinov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Aho_Corasick implements SubstringFinderInStream {
    static final int alphabet = 256 * 256;
    BufferedReader reader;
    char[] s;
    State[] states;
    int sz;

    @Override
    public ArrayList<Integer> find(InputStream input, char[] string) throws IOException {
        ArrayList<Integer> res = new ArrayList<>();
        init(input, string);
        int v = 0, cnt = 0;
        int a;
        while ((a = reader.read()) != -1) {
            char c = (char) a;
            v = go(v, c);
            cnt++;
            if (states[v].isLeaf) {
                res.add(cnt - s.length);
            }
        }
        return res;
    }

    private void init(InputStream input, char[] string) {
        s = string;
        reader = new BufferedReader(new InputStreamReader(input));
        states = new State[s.length + 1];
        sz = 1;
        for (int i = 0; i < states.length; i++) {
            states[i] = new State();
        }
        states[0].parent = states[0].link = -1;
        Arrays.fill(states[0].next, -1);
        Arrays.fill(states[0].go, -1);
        add_string(s);
    }

    private void add_string(char[] s) {
        int v = 0;
        for (char c : s) {
            if (states[v].next[c] == -1) {
                Arrays.fill(states[sz].next, -1);
                Arrays.fill(states[sz].go, -1);
                states[sz].link = -1;
                states[sz].parent = v;
                states[sz].parentChar = c;
                states[v].next[c] = sz++;
            }
            v = states[v].next[c];
        }
        states[v].isLeaf = true;
    }

    private int get_link(int v) {
        if (states[v].link == -1)
            if (v == 0 || states[v].parent == 0) {
                states[v].link = 0;
            } else {
                states[v].link = go(get_link(states[v].parent), states[v].parentChar);
            }
        return states[v].link;
    }

    private int go(int v, char c) {
        if (states[v].go[c] == -1)
            if (states[v].next[c] != -1) {
                states[v].go[c] = states[v].next[c];
            } else if (v == 0) {
                states[v].go[c] = 0;
            } else {
                states[v].go[c] = go(get_link(v), c);
            }
        return states[v].go[c];
    }

    private static class State {
        int[] next = new int[alphabet];
        boolean isLeaf;
        int parent;
        char parentChar;
        int link;
        int[] go = new int[alphabet];
    }
}
