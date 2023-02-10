package ru.nsu.fit.trubinov;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileParsing {
    static String path = "task_2_1_1/src/main/java/ru/nsu/fit/trubinov/";


    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(path + "benchmarkResults.txt", StandardCharsets.UTF_8);
        for (String s : getValues()) {
            writer.println(s);
        }
        writer.close();
    }

    public static ArrayList<String> getValues() {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(path + "benchmark.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        ArrayList<String> lines = new ArrayList<>();
        boolean flag = false;
        try {
            while ((line = reader.readLine()) != null) {
                String lastWord = line.substring(line.lastIndexOf(" ") + 1);
                if (flag) {
                    try {
                        line.substring(line.length() - 1);
                    } catch (StringIndexOutOfBoundsException e) {
                        break;
                    }
                    String[] words = line.split("\\s+");
                    lines.add(words[0].split("\\.")[1] + " " + words[3]);
                }
                if (lastWord.equals("Units")) {
                    flag = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
