package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.Animation;
import com.infected.util.MusicPlayer;
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

    public static void updateNavigationData(String destination) {
        Map.updateMap(Player.getCurrentLocation(), Location.getNextLocation(destination));
        Player.setCurrentLocation(Location.getNextLocation(destination));
        Player.raiseContaminationLevel(1);
        npcInitialize();
    }

    public static void go(String destination) {
        if (node.get(Player.getCurrentLocation()).get("nav").has(destination)) {
            if (node.get(Location.getNextLocation(destination)).get("street").asBoolean()) {
                MusicPlayer.playWalkSound();
                updateNavigationData(destination);
            } else {
                if (Location.currentCapacity(destination) <= Location.getMaxCapacityOfNextLocation(destination)) {
                    if (node.get(Location.getNextLocation(destination)).get("indoor").asBoolean()) {
                        MusicPlayer.playDoorSound();
                    } else {
                        MusicPlayer.playWalkSound();
                    }
                    updateNavigationData(destination);
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
            Animation.newPrint(sharon.getGreeting() + " " + sharon.getDialogue());
            MusicPlayer.playHospitalSound();
            Pause.pause(3000);
            Animation.newPrint("she looks and says......");
            Animation.newPrint("Oh my! You look ill let me help you");
            Pause.pause(3000);
            Nurse.giveShot();
        }
    }

    public static void clerkInitialize() {
        if (Player.getCurrentLocation().equals("groceryStore")) {
            Npc jack = new StoreClerk();
            Animation.newPrint(jack.getGreeting() + " " + jack.getDialogue());
            Pause.pause(3000);
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
        Animation.newPrint("DESCRIPTION: " + description);
    }
}