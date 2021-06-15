package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.AsciiParser;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private static final Path path = Path.of("./ascii/", "map.txt");
    private static final Path pathDefault = Path.of("./ascii/default", "map.txt");
    private static final File file = new File("./ascii/map.txt");

    public static void readMap() {
        try {
            List<String> asciiLines = AsciiParser.parse(path);
            asciiLines.forEach(System.out::println);
        } catch (IOException exception) {
            System.out.println("There was an error reading the map.");
        }
    }

    public static void updateMap(String currentLocation, String destination) {
        try {
            List<String> asciiLines = AsciiParser.parse(path);
            int[] currentCoordinates = Location.getCoords(currentLocation);
            int[] destinationCoordinates = Location.getCoords(destination);
            // remove X from current location
            List<String> removedPosition = updatePosition(asciiLines, currentCoordinates, true);
            // place X on destination
            List<String> finalAsciiMap = updatePosition(removedPosition, destinationCoordinates, false);
            // write to file with updated map
            AsciiParser.write(file, finalAsciiMap);
        } catch (IOException exception) {
            System.out.println("There was an error updating the map.");
        }
    }

    public static List<String> updatePosition(List<String> asciiLines, int[] coordinates, boolean isCurrentPosition) {
        List<String> newAsciiLines = new ArrayList<>();
        int index = 0;

        for (String line : asciiLines) {
            StringBuilder newLine = new StringBuilder(line);
            // find row position (x-coordinate)
            if (index == coordinates[0]) {
                // find column position and set to new value (y-coordinate)
                if (isCurrentPosition) {
                    newLine.setCharAt(coordinates[1], ' ');
                } else {
                    newLine.setCharAt(coordinates[1], 'x');
                }
                newAsciiLines.add(asciiLines.get(index).replace(line, newLine));
            } else {
                newAsciiLines.add(line);
            }
            index++;
        }
        return newAsciiLines;
    }

    public static void resetMap() {
        try {
            List<String> asciiLines = AsciiParser.parse(pathDefault);
            AsciiParser.write(file, asciiLines);
        } catch (IOException ioException) {
            System.out.println("There was an error resetting the map");
        }
    }
}