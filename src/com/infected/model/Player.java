package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.infected.util.Converter;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Player {
    private static final File jsonSource = new File("./data/Player.json");
    private static final File backPackSource = new File("./data/BackPack.json");
    private static final File locationItemsSource = new File("./data/LocationItems.json");

    public static JsonNode getPlayerNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static JsonNode getPlayerBackPack() {
        try {
            return TextParser.parse(backPackSource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static JsonNode getLocationItems() {
        try {
            return TextParser.parse(locationItemsSource);
        } catch (IOException ioException) {
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

    public static int getContaminationLevel() {
        return getPlayerNode().get("contaminationLevel").asInt();
    }

    public static void setContaminationLevel(int level) {
        JsonNode currContam = getPlayerNode();
        JsonNode newContam = TextParser.getNewNode(currContam, "contaminationLevel", level);
        try {
            TextParser.write(jsonSource, newContam);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void quarantine() {
        if (Player.getCurrentLocation().equals("home") && Player.getContaminationLevel() != 0) {
            setContaminationLevel(0);
            System.out.println("CONTAMINATION LEVEL: " + Player.getContaminationLevel());
            System.out.println("\n- go west \n- quit");
        } else if (!Player.getCurrentLocation().equals("home")) {
            System.out.println("MUST BE AT LOCATION HOME TO QUARANTINE");
            Navigation.routes();

        } else {
            System.out.println("CONTAMINATION LEVEL IS ALREADY AT: 0");
            Navigation.routes();
        }
    }

    public static void grabItem(String item) {
        if (Location.listItems() != null) {
            int count = getPlayerBackPack().get("mask").asInt();
            count += 1;
            JsonNode currPlayerNode = getPlayerBackPack();
            JsonNode newNode = TextParser.getNewNode(currPlayerNode, item, count);

            try {
                TextParser.write(backPackSource, newNode);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else {
            System.out.println("invalid");
        }
    }

//    public static void removeItem(String item) {
//        int countItem = getPlayerBackPack().get("home").get("mask").asInt();
//        countItem =- 1;
//        JsonNode currPlayerNode = getPlayerBackPack();
//        JsonNode newNode = TextParser.getNewNode(currPlayerNode, currPlayerNode.get("home"), countItem);
//
//        try {
//            TextParser.write(backPackSource, newNode);
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
//    }

    public static void addItem(String item) {
        JsonNode origNode = getPlayerBackPack();
        LinkedHashMap<String, Integer> map = TextParser.jsonNodeToHashMapInt(origNode);
        if (map.containsKey(item)){
            Integer count = map.get(item) + 1;
            map.put(item,count);
            JsonNode finalNode = TextParser.getDefaultObjectMapper().convertValue(map, JsonNode.class);
            try {
                TextParser.write(backPackSource,finalNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeItem(String item) {
        JsonNode origNode = getLocationItems();
        LinkedHashMap<String, Integer> map = TextParser.jsonNodeToHashMapInt(origNode);
        if (map.containsKey(item)){
            Integer count = map.get(item) - 1;
            map.put(item,count);
            JsonNode finalNode = TextParser.getDefaultObjectMapper().convertValue(map, JsonNode.class);
            try {
                TextParser.write(locationItemsSource,finalNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
