package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class Location {
    private static final File jsonSource = new File("./data/Location.json");

    public static JsonNode getLocationNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static int getCurrentCapacity(String location) {
        return getLocationNode().get(location).get("currentCapacity").asInt();
    }

    public static int getMaxCapacity(String location) {
        return getLocationNode().get(location).get("maxCapacity").asInt();
    }

    public static String getCapacityLimit(String location) {
        double capacity = (double) getCurrentCapacity(location) / (double) getMaxCapacity(location);
        DecimalFormat df = new DecimalFormat("#%");
        return df.format(capacity);
    }
}