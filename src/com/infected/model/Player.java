package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

public class Player {
    private static final File jsonSource = new File("./data/Player.json");
    private static int contaminationLevel = 5;

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

    public static int getContaminationLevel() {
        return contaminationLevel;
    }

    public static void quarantine(){
        if(Player.getCurrentLocation().equals("home") && contaminationLevel !=0){
            contaminationLevel = 0;
            System.out.println("CONTAMINATION LEVEL: "+ Player.getContaminationLevel());
            System.out.println("\n1: go west \n2: quit");
        }else if(!Player.getCurrentLocation().equals("home")){
            System.out.println("MUST BE AT LOCATION HOME TO QUARANTINE");
            Navigation.routes();

        } else{
            System.out.println("CONTAMINATION LEVEL IS ALREADY AT: 0");
            Navigation.routes();
        }
    }
}
