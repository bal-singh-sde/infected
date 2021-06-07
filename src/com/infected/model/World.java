package com.infected.model;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class World {
    private static File jsonSource = new File("./data/Worldstats.json");

    public static JsonNode getWorldStats() {
        JsonNode worldStatsNode = null;

        try {
            worldStatsNode = TextParser.parse(jsonSource);
            printWorldStats(worldStatsNode);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        return worldStatsNode;
    }

    public static List<JsonNode> getLocationNodes(JsonNode worldStatsNode) {
        List<JsonNode> locationNodes = null;
        ObjectMapper mapper = TextParser.getDefaultObjectMapper();
        String locations = worldStatsNode.get("locations").toString();

        try {
            locationNodes = mapper.readValue(locations, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return locationNodes;
    }

    public static void printWorldStats(JsonNode worldStatsNode) {
        String totalInfections = worldStatsNode.get("totalInfections").toString();
        String currentLocation = worldStatsNode.get("currentLocation").toString();
        String currentZone = worldStatsNode.get("currentZone").toString();

        System.out.println("Total Infections: " + totalInfections);
        System.out.println("Current Location: " + currentLocation);
        System.out.println("Current Zone: " + currentZone);
        System.out.println("--------------------------------------");
        System.out.println("Locations");

        List<JsonNode> locationNodes = getLocationNodes(worldStatsNode);

        for (JsonNode location : locationNodes) {
            System.out.println(
                    location.get("name") + " -" +
                            " zone: " + location.get("zone") +
                            " currentCapacity: " + location.get("currentCapacity") +
                            " maxCapacity: " + location.get("maxCapacity")
            );
        }
    }

    // Test method to update currentLocation in Worldstats.json
    public static void testWrite() {
        JsonNode currNode = getWorldStats();
        JsonNode newNode = TextParser.getNewNode(currNode, "currentLocation", "clinic");

        try {
            TextParser.write(jsonSource, newNode);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}