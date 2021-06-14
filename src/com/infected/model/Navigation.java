package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.Pause;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        if (node.get(Player.getCurrentLocation()).get("nav").has(destination)) {
            if (node.get(Location.getNextLocation(destination)).get("street").asBoolean()) {
                Player.setCurrentLocation(Location.getNextLocation(destination));
                Player.raiseContaminationLevel(1);
                npcInitialize();
            } else {
                if (Location.currentCapacity(destination) <= Location.getMaxCapacityOfNextLocation(destination)){
                    Player.setCurrentLocation(Location.getNextLocation(destination));
                    Player.raiseContaminationLevel(1);
                    npcInitialize();
                } else {
                    System.out.println("Current capacity of " + Location.getNextLocation(destination) + " is " + Location.currentCapacity(destination) + ". Max capacity is " + Location.getMaxCapacity(Player.getCurrentLocation()));
                }
            }
        }
    }

    public static void npcInitialize() {
        nurseInitialize();
        clerkInitialize();
    }

    public static void nurseInitialize() {
        if (Player.getCurrentLocation().equals("clinic")) {
            Npc sharon = new Nurse();
            System.out.println(sharon.getGreeting() + " " + sharon.getDialogue());
            Pause.pause();
            System.out.println("she looks and says......");
            System.out.println("Oh my! You look ill let me help you");
            Pause.pause();
            Nurse.giveShot();
        }
    }

    public static void clerkInitialize() {
        if (Player.getCurrentLocation().equals("groceryStore")) {
            Npc jack = new StoreClerk();
            System.out.println(jack.getGreeting() + " " + jack.getDialogue());
            Pause.pause();
            StoreClerk.cough();
        }
    }

    public static void routes() {
        String infectedLevel = "CURRENT CONTAMINATION LEVEL: " + Player.getContaminationLevel();
        System.out.println(infectedLevel);
        String description = "You are at " + Player.getCurrentLocation() + ".";
        description += " " + Location.listDirections(Player.getCurrentLocation());
        List<String> items = Location.findItems(Player.getCurrentLocation());

        if (items.size() > 0) {
            description += " You see the following items: ";
            description += items.toString();
        }
        System.out.println("DESCRIPTION: " + description);

    }
}