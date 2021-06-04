package com.infected.model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class World {
    public static void getWorldStats() {
        String rootPath = new File("").getAbsolutePath(); // project root folder
        File jsonSource = new File(rootPath + "/data/Worldstats.json");

        try {
            JsonNode worldStatsNode = TextParser.parse(jsonSource);
            String totalInfections = worldStatsNode.get("totalInfections").toString();
            String currentLocation = worldStatsNode.get("currentLocation").toString();
            String currentZone = worldStatsNode.get("currentZone").toString();

            ObjectMapper mapper = TextParser.getDefaultObjectMapper();
            String locations = worldStatsNode.get("locations").toString();
            List<JsonNode> locationList = mapper.readValue(locations, new TypeReference<>() {});

            System.out.println("Total Infections: " + totalInfections);
            System.out.println("Current Location: " + currentLocation);
            System.out.println("Current Zone: " + currentZone);
            System.out.println("--------------------------------------");
            System.out.println("Locations");

            for (JsonNode location : locationList) {
                System.out.println(
                        location.get("name") + " -" +
                                " zone: " + location.get("zone") +
                                " currentCapacity: " + location.get("currentCapacity") +
                                " maxCapacity: " + location.get("maxCapacity")
                );
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}