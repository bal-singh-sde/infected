package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.Animation;
import com.infected.util.TextParser;
import java.util.LinkedHashMap;

public class World {
    public static void printWorldStats() {
        printCapacityLimits();
    }

    public static void printCapacityLimits() {
        Animation.printBox("World Stats","Location Capacity Limits");
        JsonNode locationNode = Location.getLocationNode();
        LinkedHashMap<String, Object> locationMap = TextParser.jsonNodeToHashMap(locationNode);

        for (String locationName : locationMap.keySet()) {
            if (!locationNode.get(locationName).get("street").asBoolean()) {
                System.out.println(
                        locationName + " capacity: " +
                                Location.getCapacityLimit(locationName)
                );
            }
        }
    }
}