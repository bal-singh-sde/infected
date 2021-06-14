package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Player {
    private static final File jsonSource = new File("./data/Player.json");
    private static final File jsonSourceDefault = new File("./data/default/Player.json");
    private static final File backPackSource = new File("./data/Backpack.json");
    private static final File backPackSourceDefault = new File("./data/default/Backpack.json");


    public static JsonNode getPlayerNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static JsonNode getPlayerNodeDefault() {
        try {
            return TextParser.parse(jsonSourceDefault);
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

    public static JsonNode getPlayerBackPackDefault() {
        try {
            return TextParser.parse(backPackSourceDefault);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }


    public static String getCurrentLocation() {
        return getPlayerNode().get("currentLocation").textValue();
    }

    public static void setCurrentLocation(String newLocation) {
        JsonNode newNode = overWriteLocationSetup(newLocation);

        try {
            TextParser.write(jsonSource, newNode);
            Game.saveGame();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static int getContaminationLevel() {
        return getPlayerNode().get("contaminationLevel").asInt();
    }

    public static void setContaminationLevel(int level) {
        if (level < 0) return;

        JsonNode newContam = overWriteContaminationSetup(level);
        try {
            TextParser.write(jsonSource, newContam);
            Game.saveGame();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static JsonNode overWriteLocationSetup(String value) {
        //overwrite josn file with new string value
        JsonNode currContam = getPlayerNode();
        JsonNode newContam = TextParser.getNewNode(currContam, "currentLocation", value);

        return newContam;
    }

    public static JsonNode overWriteContaminationSetup(int value) {
        //overwrite josn file with new int value
        JsonNode currContam = getPlayerNode();
        JsonNode newContam = TextParser.getNewNode(currContam, "contaminationLevel", value);

        return newContam;
    }

    public static void raiseContaminationLevel(int value) {
        // raises contamination level
        if (value < 0) return;
        int currentLevel = getContaminationLevel();
        currentLevel += value;
        if(currentLevel >= 15){
            System.out.println("Warning!!!!. If Player contamination reaches 20 then player will lose game.");
        }
        try {
            TextParser.write(jsonSource, overWriteContaminationSetup(currentLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void lowerContaminationLevel(int value) {
        // lower contamination level
        if (value < 0) return;
        int currentLevel = getContaminationLevel();
        //can not have negative contamination level
        if (currentLevel <= value) {
            currentLevel = 0;
        } else {
            currentLevel -= value;
        }
        try {
            TextParser.write(jsonSource, overWriteContaminationSetup(currentLevel));
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void resetPlayer() {
        JsonNode newPlayerNode = getPlayerNodeDefault();
        JsonNode newPlayerBackPackNode = getPlayerBackPackDefault();

        try {
            TextParser.write(jsonSource, newPlayerNode);
            TextParser.write(backPackSource, newPlayerBackPackNode);
            Location.resetLocationItems();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void addItem(String item) {
        JsonNode origNode = getPlayerBackPack();
        LinkedHashMap<String, Integer> map = TextParser.jsonNodeToHashMapInt(origNode);
        if (map.containsKey(item) && Objects.requireNonNull(Location.getLocationItemsNode()).get(Player.getCurrentLocation()).get(item).asInt() >= 1) {
            if (map.get(item) < 5) {
                map.put(item, map.get(item) + 1);
                JsonNode finalNode = TextParser.getDefaultObjectMapper().convertValue(map, JsonNode.class);
                try {
                    TextParser.write(backPackSource, finalNode);
                    Location.removeItem(item);
                    Game.saveGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Your mask pocket is full");
            }
        } else {
            System.out.println("No items at Location");
        }
    }

    public static void useItem(String item) {
        lowerContaminationLevel(1);
        deleteItem(item);
    }

    public static void deleteItem(String item) {
        JsonNode origNode = getPlayerBackPack();
        LinkedHashMap<String, Integer> map = TextParser.jsonNodeToHashMapInt(origNode);
        if (map.containsKey(item)) {
            if (map.get(item) >= 1 && map.get(item) <= 5) {
                map.put(item, map.get(item) - 1);
            }
            JsonNode finalNode = TextParser.getDefaultObjectMapper().convertValue(map, JsonNode.class);
            try {
                TextParser.write(backPackSource, finalNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Item not in the bag pack");
        }
    }
}
