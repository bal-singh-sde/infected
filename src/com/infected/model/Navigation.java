package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

public class Navigation {
    File jsonSource = new File("./data/Location.json");
    String currentLocation = "home";
    public void go(String destination) {
        try {
            JsonNode node = TextParser.parse(jsonSource);
            if (node.get(currentLocation).get("nav").has(destination)){
                currentLocation = String.valueOf(node.get(currentLocation).get("nav").get(destination)).replaceAll("\"", "");
                System.out.println(currentLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
