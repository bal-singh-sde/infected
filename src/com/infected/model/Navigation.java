package com.infected.model;
import com.fasterxml.jackson.databind.JsonNode;
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
        String cLo = null;
        if (node.get(Player.getCurrentLocation()).get("nav").has(destination)) {
            cLo = String.valueOf(node.get(Player.getCurrentLocation()).get("nav").get(destination)).replaceAll("\"", "");
            Player.setCurrentLocation(cLo);
            Player.raiseContaminationLevel(1);
           nurseInitialize();
        }
    }
    public static void nurseInitialize(){
        if (Player.getCurrentLocation().equals("clinic")) {
           Npc becky = new Nurse();
            System.out.println(becky.getGreeting()+" "+becky.getDialogue());
        try {
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
            System.out.println("she looks and says......");
            System.out.println("Oh my! You look ill let me help you");
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        Nurse.giveShot();
        }
    }

    public static void routes () {
        String infectedLevel = "CURRENT CONTAMINATION LEVEL: "+Player.getContaminationLevel();
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

