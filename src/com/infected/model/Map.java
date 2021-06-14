package com.infected.model;

import com.infected.util.AsciiParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Map {
    private static final Path path = Path.of("./ascii/", "map.txt");
    private static final File file = new File("./ascii/map.txt");

    public static void readMap() {
        try {
            AsciiParser.parse(path);
        } catch (IOException exception) {
            System.out.println("There was an error reading the map.");
        }
    }

    public static void updateMap(String string) {
        try {
            AsciiParser.write(file, string);
        } catch (IOException exception) {
            System.out.println("There was an error updating the map.");
        }
    }
}