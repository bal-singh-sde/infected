package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;
import java.io.File;
import java.io.IOException;

public class Navigation {
    private static final File jsonSource = new File("./data/Location.json");

    private static JsonNode node;

    static {
        try {
            node = TextParser.parse(jsonSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void go(String destination) {
        String cLo = null;
        if (node.get(Player.getCurrentLocation()).get("nav").has(destination)) {
            cLo = String.valueOf(node.get(Player.getCurrentLocation()).get("nav").get(destination)).replaceAll("\"", "");
            Player.setCurrentLocation(cLo);
            Player.raiseContaminationLevel(1);
        }
    }

    public static void routes () {
        String route;
        String infectedLevel = "CURRENT CONTAMINATION LEVEL: "+Player.getContaminationLevel();
        String description = node.get(Player.getCurrentLocation()).get("description").toString().replaceAll(",","\n").replaceAll("\"","");
        route = node.get(Player.getCurrentLocation()).get("nav").toString().replaceAll("[{}]","").replaceAll(",","\n");
        System.out.println(infectedLevel);
        System.out.println("DESCRIPTION: \n"+ description);
        System.out.println("DIRECTIONS YOU CAN GO: \n" + route);
    }
}

