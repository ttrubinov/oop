package ru.nsu.fit.trubinov;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public interface SubstringFinderInStream {
    ArrayList<Integer> find(InputStream input, char[] substring) throws IOException;
}