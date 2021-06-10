package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;

public class StoreClerk extends Npc{
    JsonNode clerkNode= getNpcNode();

    @Override
    public  String getGreeting(){
        return getNpcNode().get("groceryStore").get("greeting").textValue();
    }
    @Override
    public  String getDialogue(){
        return getNpcNode().get("groceryStore").get("dialogue").textValue();
    }
    @Override
    public  int getRisk(){
        return getNpcNode().get("groceryStore").get("risk").asInt();
    }
}