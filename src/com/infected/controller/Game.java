package com.infected.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.model.Navigation;
import com.infected.model.Player;
import com.infected.model.World;
import com.infected.util.TextParser;
import com.infected.util.TextScanner;
import static com.infected.model.Game.*;

import java.io.File;
import java.io.IOException;

public class Game {
    public void start() {
        if (isGameSaved()) {
            promptGameStatus();
        } else {
            printIntro();
        }

        while (true) {
            boolean secondShot = false;

            if (Player.getCurrentLocation().equals("clinic")) {
                secondShot = true;
            }

            if (secondShot) {
                System.out.println("You won the game!");
                System.exit(0);
            }

            String[] commandArray = TextScanner.newScanner();
            commandArray[0] = getProcessedVerb(commandArray[0]);

            if ("quit".equals(commandArray[0])) {
                System.exit(0);
            } else if ("go".equals(commandArray[0])) {
                // TODO: check if move is valid. if move is valid, move player to new location else display error
                Navigation.go(commandArray[1]);
                printCurrentLocation();
            } else if ("get".equals(commandArray[0])) {
                // TODO: check if item is in room. if item is valid, pick up item, else display error
            } else if ("list".equals(commandArray[0])) {
                // TODO: check if list item exists. if list item exists, list the item, else display error
                if ("worldstats".equals(commandArray[1])) {
                    World.printWorldStats();
                }
            } else if ("quarantine".equals(commandArray[0])) {
                Player.quarantine();
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private void printIntro() {
        System.out.println("# Welcome to the World of INFECTED!!!!!!!");
        System.out.println("\nA new virus have spread around the community and there are chances that you can get infected. \n" +
                "\nYou are at home you have the option to quit, quarantine(lower contamination level to 0)or go west. \nSelect the following command:");
        System.out.println("\n- go west \n- quit \n- quarantine");
    }

    private void printCurrentLocation() {
        System.out.println("Current location: " + Player.getCurrentLocation());
        Navigation.routes();
    }

    private void promptGameStatus() {
        System.out.println("Current game is in progress. Would you like to resume previous game or start a new game?");
        System.out.println("\n- resume game \n- reset game");

        while (true) {
            String[] commandArray = TextScanner.newScanner();
            commandArray[0] = getProcessedVerb(commandArray[0]);

            if ("resume".equals(commandArray[0])) {
                if ("game".equals(commandArray[1])) {
                    printCurrentLocation();
                    break;
                }
            } else if ("reset".equals(commandArray[0])) {
                if ("game".equals(commandArray[1])) {
                    clearGameData();
                    printIntro();
                    break;
                }
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private String getProcessedVerb(String input) {
        File jsonFile = new File("data/Cmd.json");
        try {
            JsonNode node = TextParser.parse(jsonFile);
            if(node.has(input)){
                return node.get(input).textValue();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}