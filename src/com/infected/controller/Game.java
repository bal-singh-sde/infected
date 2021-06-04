package com.infected.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.infected.model.World;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Game {
    public void start() {
        isSynonym( "back");
        Scanner scan = new Scanner(System.in);

        System.out.println("# Welcome to the World of INFECTED!!!!!!!");
        // TODO: make location description dynamic based on current location
        System.out.println("\nA new virus have spread around the community and there are chances that you can get infected. \n" +
                "\nYou are at home you have the option to quarantine(lower contamination level to 0)or go west. \nSelect the following command:");
        // TODO: need to make valid commands dynamic based on current location
        System.out.println("\n1: goWest \n2: quarantine \n3: q to Quit ");
        System.out.print("Enter your command here: ");

        while (true) {
            boolean secondShot = false;
            // "go west"
            String command = scan.nextLine();
            // "go west" -> ["go", "west"]
            String[] commandArray = command.toLowerCase().split(" ");

            if ("quit".equals(commandArray[0])) {
                System.exit(0);
            }

            // TODO: vaccine logic that toggles secondShot
            if (secondShot) {
                System.out.println("You won the game!");
                System.exit(0);
            }

            // TODO: figure out connecting synonym logic
            if ("go".equals(commandArray[0])) {
                // TODO: check if move is valid. if move is valid, move player to new location else display error
            }

            if ("get".equals(commandArray[0])) {
                // TODO: check if item is in room. if item is valid, pick up item, else display error
            }

            if ("list".equals(commandArray[0])) {
                // TODO: check if list item exists. if list item exists, list the item, else display error
                if ("worldstats".equals(commandArray[1])) {
                    World.getWorldStats();
                }
            }
        }

    }
public boolean isSynonym(String input){

    //synonym experiments
    File jsonFile = new File("data/Commands.json");
    try {
        JsonNode node = TextParser.parse(jsonFile);

        for(int i =0; i <(node.get("commands").get("go").get("synonyms").size());i++){
            String tempGo = node.get("commands").get("go").get("synonyms").get(i).toString();
            String subTempGo = tempGo.substring(1,tempGo.length()-1);
            if(input.equals(subTempGo)){
                System.out.println(input + " :is a synonym for go");
                return true;
            }
        }
        for(int i =0; i <(node.get("commands").get("get").get("synonyms").size());i++){
            String tempGet = node.get("commands").get("get").get("synonyms").get(i).toString();
            String subTempGet = tempGet.substring(1,tempGet.length()-1);
            if(input.equals(subTempGet)){
                System.out.println(input + " :is a synonym for get");
                return true;
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }
    System.out.println(input + " :not found");
    return false;
}
}