import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
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
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SubstringTest {
    public static Stream<Arguments> findingAlgorithms() {
        return Stream.of(
                arguments(new AhoCorasick(), "aaaaaa", List.of(1, 2)),
                arguments(new AhoCorasick(), "b", List.of(0)),
                arguments(new AhoCorasick(), "g", List.of(8)),
                arguments(new AhoCorasick(), "a", List.of(1, 2, 3, 4, 5, 6, 7)),
                arguments(new KnuthMorrisPratt(), "aaaaaa", List.of(1, 2)),
                arguments(new KnuthMorrisPratt(), "b", List.of(0)),
                arguments(new KnuthMorrisPratt(), "g", List.of(8)),
                arguments(new KnuthMorrisPratt(), "a", List.of(1, 2, 3, 4, 5, 6, 7)));
    }


    @ParameterizedTest
    @MethodSource("findingAlgorithms")
    void finderTest(SubstringFinderInStream s, String pattern, List<Integer> a) throws IOException {
        File f = new File("./src/main/java/ru/nsu/fit/trubinov/input.txt");
        File stringToFind = new File("./src/main/java/ru/nsu/fit/trubinov/input2.txt");
        InputStream stream = new FileInputStream(f);
        String c = Files.readString(stringToFind.toPath());
        assertEquals(s.find(stream, c), List.of(5984668));
        f = new File("./src/main/java/ru/nsu/fit/trubinov/input3.txt");
        stream = new FileInputStream(f);
        assertEquals(s.find(stream, pattern), a);
    }
}