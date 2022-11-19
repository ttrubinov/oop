package ru.nsu.fit.trubinov;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException {
        File f = new File("./task_1_3_1/src/main/java/ru/nsu/fit/trubinov/input.txt");
        File f1 = new File("./task_1_3_1/src/main/java/ru/nsu/fit/trubinov/input2.txt");
        InputStream stream = new FileInputStream(f);
        String c = Files.readString(f1.toPath());
        SubstringFinderInStream s = new KnuthMorrisPratt();
        System.out.println(s.find(stream, ("@bG8`ddn^75F9@:kIh<FtkC}0Q^'(/wMpy\\8/w.U=*}pO:").toCharArray()));
    }
}