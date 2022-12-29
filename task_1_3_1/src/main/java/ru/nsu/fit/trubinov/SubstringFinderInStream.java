package ru.nsu.fit.trubinov;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Find all occurrences of a substring in a stream.
 */
public interface SubstringFinderInStream {
    /**
     * Find indices of all occurrences of an input substring
     * in an input stream.
     *
     * @param input     input stream
     * @param substring substring to find in the stream
     * @return indices of all occurrences of the substring
     * @throws IOException if something went wrong with reading the stream
     */
    List<Integer> find(InputStream input, String substring) throws IOException;
}