package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.MusicPlayer;
import com.infected.util.Pause;

public class Nurse extends Npc {
    JsonNode nurseNode = getNpcNode();

    @Override
    public String getGreeting() {
        return getNpcNode().get("clinic").get("greeting").textValue();
    }

    @Override
    public String getDialogue() {
        return getNpcNode().get("clinic").get("dialogue").textValue();
    }

    @Override
    public int getRisk() {
        return getNpcNode().get("clinic").get("risk").asInt();
    }

    public static void giveShot() {
        MusicPlayer.winSound();
        Pause.pause(6000);
        Player.setContaminationLevel(0);
        System.out.println("You won the game!");
        Game.clearGameData();
        System.exit(0);
    }

}