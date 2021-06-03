package com.infected.controller;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    public void start() {
        System.out.println("Hello World");
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