package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

public class Player {
    private static final File jsonSource = new File("./data/Player.json");

    public static JsonNode getPlayerNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static String getCurrentLocation() {
        return getPlayerNode().get("currentLocation").textValue();
    }

    public static void setCurrentLocation(String newLocation) {
        JsonNode currPlayerNode = getPlayerNode();
        JsonNode newNode = TextParser.getNewNode(currPlayerNode, "currentLocation", newLocation);

        try {
            TextParser.write(jsonSource, newNode);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
