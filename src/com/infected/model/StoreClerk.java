package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.Animation;
import com.infected.util.MusicPlayer;
import com.infected.util.Pause;

public class StoreClerk extends Npc {
    JsonNode clerkNode = getNpcNode();

    @Override
    public String getGreeting() {
        return getNpcNode().get("groceryStore").get("greeting").textValue();
    }

    @Override
    public String getDialogue() {
        return getNpcNode().get("groceryStore").get("dialogue").textValue();
    }

    @Override
    public int getRisk() {
        return getNpcNode().get("groceryStore").get("risk").asInt();
    }

    public static void cough() {
        MusicPlayer.playCoughSound();
        System.out.println("........ACHOO!!");
        Player.raiseContaminationLevel(3);
        Animation.newPrint("Sorry..excuse me");
        Pause.pause(3000);
    }
}