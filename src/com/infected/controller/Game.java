package com.infected.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Game {
    public void start() {
        isSynonym( "back");
        Scanner scan = new Scanner(System.in);

        System.out.println("# Welcome to the World of INFECTED!!!!!!!");
        System.out.println("\nA new virus have spread around the community and there are chances that you can get infected. \n" +
                "\nYou are at home you have the option to quarantine(lower contamination level to 0)or go west. \nSelect the following command:");
        System.out.println("\n1: goWest \n2: quarantine \n3: q to Quit ");
        System.out.print("Enter your command here: ");
        String input = scan.nextLine();
        if(input.equals("q")) {
            System.exit(0);
         }
        else if(input.equals("goWest")){
            System.out.println("You are on Main Block Two. You have the option to go east, west, or north");
        }
        else if(input.equals("quarantine")){
             System.out.println("You are quarantined. Your contamination level is 0. You have the option to go west or quit");
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