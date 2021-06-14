package com.infected.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AsciiParser {
    public static void parse(Path path) {
        try {
            Files.lines(path).forEach(System.out::println);
        } catch(IOException e) {
            System.out.println("There was an error reading the file.");
        }
    }
}