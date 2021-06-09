package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class Location {
    private static final File jsonSource = new File("./data/Location.json");
    private static final File locationItemsSource = new File("./data/LocationItems.json");

    public static JsonNode getLocationNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static JsonNode getLocationItemsNode() {
        try {
            return TextParser.parse(locationItemsSource);
        } catch (IOException ioException) {
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

    static void removeItem(String item) {
        JsonNode origNode = getLocationItemsNode();
        LinkedHashMap<String, LinkedHashMap<String, Integer>> map = TextParser.jsonNodeToHashMapNested(origNode);
        if (map.containsKey("home")) {
            if (map.get("home").get(item) >= 1) {
                map.get("home").put(item, map.get("home").get(item) - 1);

                JsonNode finalNode = TextParser.getDefaultObjectMapper().convertValue(map, JsonNode.class);
                try {
                    TextParser.write(locationItemsSource, finalNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("No more items");
            }
        }
    }
}