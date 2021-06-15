package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

 abstract class Npc {
    private static final File npcSource = new File("data/Npc.json");

    public static JsonNode getNpcNode(){
        try {
            return TextParser.parse(npcSource);
        }catch (IOException err){
            err.printStackTrace();
        }
        return null;
    }
    public abstract String getGreeting();
    public abstract String getDialogue();
    public abstract int getRisk();

}