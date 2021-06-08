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
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static String getCurrentLocation() {
        return getPlayerNode().get("currentLocation").textValue();
    }

    public static void setCurrentLocation(String newLocation) {
//        JsonNode currPlayerNode = getPlayerNode();
        JsonNode newNode = overWriteLocationSetup(newLocation);//TextParser.getNewNode(currPlayerNode, "currentLocation", newLocation);

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
        JsonNode newContam = overWriteContaminationSetup(level); //TextParser.getNewNode(currContam, "contaminationLevel", level);
        try {
            TextParser.write(jsonSource, newContam);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    public static JsonNode overWriteLocationSetup(String value){
        //overwrite josn file with new string value
        JsonNode currContam = getPlayerNode();
        JsonNode newContam = TextParser.getNewNode(currContam, "currentLocation", value);

        return newContam;
    }
    public static JsonNode overWriteContaminationSetup(int value){
        //overwrite josn file with new int value
        JsonNode currContam = getPlayerNode();
        JsonNode newContam = TextParser.getNewNode(currContam, "contaminationLevel", value);

        return newContam;
    }
    public static void raiseContaminationLevel(int value){
        // raises contamination level
        int currentLevel = getContaminationLevel();
        currentLevel += value;
        try {
            TextParser.write(jsonSource, overWriteContaminationSetup(currentLevel));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void lowerContaminationLevel(){
        // lower contamination level
        int currentLevel = getContaminationLevel();
        //can not have negative contamination level
        if(currentLevel <= 4){
            currentLevel = 0;
        }else {
            currentLevel -= 4;
        }
        try {
            TextParser.write(jsonSource, overWriteContaminationSetup(currentLevel));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void quarantine() {
        if (Player.getCurrentLocation().equals("home") && Player.getContaminationLevel() != 0) {
            setContaminationLevel(0);
            System.out.println("CONTAMINATION LEVEL: " + Player.getContaminationLevel());
            System.out.println("\n1: go west \n2: quit");
        } else if (!Player.getCurrentLocation().equals("home")) {
            System.out.println("MUST BE AT LOCATION HOME TO QUARANTINE");
            Navigation.routes();

        } else {
            System.out.println("CONTAMINATION LEVEL IS ALREADY AT: 0");
            Navigation.routes();
        }
    }
    //working on function now
    public static void locationHasItem(){
        File jsonSource = new File("./data/Player.json");
    }
}
