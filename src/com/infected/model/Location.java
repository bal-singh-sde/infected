package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Location {
    private static final File jsonSource = new File("./data/Location.json");
    private static final File locationItemsSource = new File("./data/LocationItems.json");
    private static final File locationsItemsSourceDefault = new File("./data/default/LocationItems.json");

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


    public static JsonNode getLocationItemsNodeDefault() {
        try {
            return TextParser.parse(locationsItemsSourceDefault);
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
        if (map.containsKey(Player.getCurrentLocation())) {
            if (map.get(Player.getCurrentLocation()).get(item) >= 1) {
                map.get(Player.getCurrentLocation()).put(item, map.get(Player.getCurrentLocation()).get(item) - 1);

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

    static void resetLocationItems() {
    JsonNode newLocationItemsNode = getLocationItemsNodeDefault();

    try {
        TextParser.write(locationItemsSource, newLocationItemsNode);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> findItems(String location) {
        List<String> items = new ArrayList<>();
        JsonNode locationItemsNode = getLocationItemsNode();
        LinkedHashMap<String, Integer> locationItemMap = TextParser.jsonNodeToHashMapInt(locationItemsNode.get(location));

        if (locationItemMap != null) {
            items = locationItemMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > 0)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return items;
    }

    public static String listDirections(String location) {
        String directions = "";
        JsonNode locationNode = getLocationNode().get(location).get("nav");
        LinkedHashMap<String, Object> locationMap = TextParser.jsonNodeToHashMap(locationNode);

        directions = locationMap.entrySet().stream()
                .map(entry -> entry.getValue() + " is " + entry.getKey() + ", ")
                .collect(Collectors.joining());

        directions = directions.substring(0, directions.length() - 2) + '.';

        return directions;
    }

    public static List<String> findCommands(String location) {
        JsonNode locationNode = getLocationNode().get(location);
        return TextParser.jsonNodeToListString(locationNode.get("commands"));
    }
}