package com.infected.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.model.*;
import com.infected.util.TextParser;
import com.infected.util.TextScanner;
import static com.infected.model.Game.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
            } else if(Player.getContaminationLevel() >= 20 ){
                System.out.println("Sorry!!!You lost!!! Please try again.");
                com.infected.model.Game.clearGameData();
                System.exit(0);
            } else if ("help".equals(commandArray[0])) {
                help(Player.getCurrentLocation());
            } else if ("go".equals(commandArray[0])) {
                Navigation.go(commandArray[1]);
                Navigation.routes();
            } else if ("get".equals(commandArray[0])) {
                Player.addItem(commandArray[1]);
                System.out.println("Items in your BackPack: " + Player.getPlayerBackPack().toString());
            } else if ("show".equals(commandArray[0])) {
                if ("worldstats".equals(commandArray[1])) {
                    World.printWorldStats();
                } else if ("map".equals(commandArray[1])) {
                    Map.readMap();
                } else {
                    System.out.println("Invalid command");
                }
            } else if ("use".equals(commandArray[0])){
                Player.useItem(commandArray[1]);
            } else if ("quarantine".equals(commandArray[0])) {
                Player.quarantine();
            } else {
                System.out.println("Invalid command");
            }
        }
    }

    private void printIntro() {
        System.out.println("# Welcome to the World of INFECTED!!!!!!!");
        System.out.println("\nA new virus have spread around the community and there are chances that you can get infected.");
        Navigation.routes();
    }

    private void promptGameStatus() {
        System.out.println("Current game is in progress. Would you like to resume previous game or start a new game?");
        System.out.println("\n- resume game \n- reset game");

        while (true) {
            String[] commandArray = TextScanner.newScanner();
            commandArray[0] = getProcessedVerb(commandArray[0]);

            if (commandArray.length == 2 && "resume".equals(commandArray[0])) {
                if ("game".equals(commandArray[1])) {
                    Navigation.routes();
                    break;
                }
            } else if (commandArray.length == 2 && "reset".equals(commandArray[0])) {
                if ("game".equals(commandArray[1])) {
                    clearGameData();
                    printIntro();
                    break;
                }
            } else {
                System.out.println("Invalid command. Valid commands: <reset game> or <resume game>");
            }
        }
    }

    private String getProcessedVerb(String input) {
        File jsonFile = new File("data/Cmd.json");
        try {
            JsonNode node = TextParser.parse(jsonFile);
            if (node.get("synonyms").has(input)){
                return node.get("synonyms").get(input).textValue();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static void help(String location) {
        JsonNode cmdNode = getCmdNode();
        List<String> commands = Location.findCommands(location);
        List<String> globalCommands = TextParser.jsonNodeToListString(cmdNode.get("globalCommands"));

        System.out.println("Available Commands");

        // commands available at current location
        if (commands.size() > 0) {
            for (String command : commands) {
                System.out.println("- " + command);
            }
        }

        // global commands
        for (String globalCommand : globalCommands) {
            System.out.println("- " + globalCommand);
        }
    }
}