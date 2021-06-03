package com.infected.controller;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Scanner;

public class Game {
    public void start() {
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
    public boolean isSynonymGet(String input){
        //create String array to hold the synonyms for go
        String [] getSynonyms= {"get","take","remove","acquire"};

        //send array to list to allow us to use java's .contains methods
        List<String> getList= new ArrayList<>(Arrays.asList(getSynonyms));

        //check if input is in goList :go synonym
        if(getList.contains(input)) return true;
        return false;
    }
    public boolean isSynonymGo(String input){
        //create String array to hold the synonyms for go
        String [] goSynonyms= {"go","walk","run","hike","skip"};

        //send array to list to allow us to use java's .contains methods
        List<String> goList= new ArrayList<>(Arrays.asList(goSynonyms));

        //check if input is in goList :go synonym
        if(goList.contains(input)) return true;
        return false;
    }
}