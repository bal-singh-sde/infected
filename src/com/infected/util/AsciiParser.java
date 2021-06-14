package com.infected.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class AsciiParser {
    public static void parse(Path path) throws IOException {
        Files.lines(path).forEach(System.out::println);
    }

    public static void write(File src, String output) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(src));
        out.println(output);
    }
}