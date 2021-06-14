package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;
import java.util.LinkedHashMap;

public class World {
    public static void printWorldStats() {
        System.out.println("World Stats");
        printCapacityLimits();
    }

    public static void printCapacityLimits() {
        System.out.println("-----------------------------");
        System.out.println("Location Capacity Limits");
        System.out.println("-----------------------------");
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