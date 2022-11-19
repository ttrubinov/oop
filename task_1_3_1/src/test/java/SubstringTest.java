import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nsu.fit.trubinov.AhoCorasick;
import ru.nsu.fit.trubinov.KnuthMorrisPratt;
import ru.nsu.fit.trubinov.SubstringFinderInStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstringTest {
    public static Stream<SubstringFinderInStream> findingAlgorithms() {
        return Stream.of(new AhoCorasick(), new KnuthMorrisPratt());
    }

    @ParameterizedTest
    @MethodSource("findingAlgorithms")
    void finderTest(SubstringFinderInStream s) throws IOException {
        File f = new File("./src/main/java/ru/nsu/fit/trubinov/input.txt");
        File stringToFind = new File("./src/main/java/ru/nsu/fit/trubinov/input2.txt");
        InputStream stream = new FileInputStream(f);
        String c = Files.readString(stringToFind.toPath());
        assertEquals(s.find(stream, c.toCharArray()), List.of(5984668));
        f = new File("./src/main/java/ru/nsu/fit/trubinov/input3.txt");
        stream = new FileInputStream(f);
        assertEquals(s.find(stream, "aaaaaa".toCharArray()), List.of(1, 2));
        stream = new FileInputStream(f);
        assertEquals(s.find(stream, "a".toCharArray()), List.of(1, 2, 3, 4, 5, 6, 7));
        stream = new FileInputStream(f);
        assertEquals(s.find(stream, "b".toCharArray()), List.of(0));
        stream = new FileInputStream(f);
        assertEquals(s.find(stream, "g".toCharArray()), List.of(8));
    }
}