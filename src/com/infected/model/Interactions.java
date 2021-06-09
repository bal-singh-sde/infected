package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

public class Interactions {
    private static final File jsonSource = new File("./data/Location.json");

    private static JsonNode node;

    static {
        try {
            node = TextParser.parse(jsonSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void get(String item) {
        int size =node.get(Player.getCurrentLocation()).get("item").size();
        for(int i=0; i<size;i++){
            if(node.get(Player.getCurrentLocation()).get("item").get(i).textValue().equals(item)){
                Player.lowerContaminationLevel();
                String route;
                String infectedLevel = "CURRENT CONTAMINATION LEVEL: "+Player.getContaminationLevel();
                String description = node.get(Player.getCurrentLocation()).get("description").toString().replaceAll(",","\n").replaceAll("\"","");
                route = node.get(Player.getCurrentLocation()).get("nav").toString().replaceAll("[{}]","").replaceAll(",","\n");
                System.out.println(infectedLevel);
                System.out.println("DESCRIPTION: \n"+ description);
                System.out.println("DIRECTIONS YOU CAN GO: \n" + route);
            }
        }
    }
}
