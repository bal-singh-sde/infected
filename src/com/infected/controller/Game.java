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
        Scanner scan = new Scanner(System.in);

        System.out.println("# Welcome to the World of INFECTED!!!!!!!");
        System.out.println("\nA new virus have spread around the community and there are chances that you can get infected. \n" +
                "\nYou are at home you have the option to quarantine(lower contamination level to 0)or go west. \nSelect the following command:");
       // CODE ADDED BELOW -PATWIL
        boolean validCmd = false;
        while (!validCmd) {
            System.out.println("\n1: go west \n2: quarantine \n3: q to Quit ");
            System.out.print("Enter your command here: ");
            String input = scan.nextLine();
            String [] inputArr = input.split(" ");

            if(input.equals("q")) {
                System.exit(0);
            }
            if(inputArr[0].equals("quarantine")){
                //lower contamination level
                System.out.println("You are quarantined. Your contamination level is 0. You have the option to go west or quit");
            }
            if (isSynonym(inputArr)) {
                System.out.println("your good");
                validCmd = true;
            }

            System.out.println(Arrays.toString(inputArr)); //<WAS MAKING SURE IT CHANGED THE VALUE OF ARRAY[0]

            /*
            *BELOW IS WORK BELONGING TO RASOJ DIDN'T WANT TO DELETE HIS WORK WITH OUT TALKING TO HIM AND THE
            * REST OF THE GROUP FIRST
            *
            * else if(input.equals("goWest")){
            * System.out.println("You are on Main Block Two. You have the option to go east, west, or north");
            * }
            * else if(input.equals("quarantine")){
            *  System.out.println("You are quarantined. Your contamination level is 0. You have the option to go west or quit");
            *   }
            *
             */
        }

    }
    /*
    *THE METHOD BELOW MAY NOT BE NEEDED MAY ID INCASE  WE DECIDE WE DONT WANT VALID COMMAND LOGIC
    * IN START METHOD ALL NEW WORK SHOULD BE IN NEW CLASS
     */
    public boolean checkInput(String [] input){
        if (isSynonym(input)) {
            System.out.println("your good");
            return true;
        }return false;
    }
    //CHECK IF INPUT IS SYNONYM FOR GO OR GET
public boolean isSynonym(String [] input){
    File jsonFile = new File("data/Commands.json");
    try {
        JsonNode node = TextParser.parse(jsonFile);

        for(int i =0; i <(node.get("commands").get("go").get("synonyms").size());i++){
            String tempGo = node.get("commands").get("go").get("synonyms").get(i).toString();
            String subTempGo = tempGo.substring(1,tempGo.length()-1);
            if(input[0].equals(subTempGo)){
                System.out.println(input[0] + " :is a synonym for go");
                    input[0] = "go";
//                System.out.println(input[0]);  <WAS MAKING SURE IT CHANGED THE VALUE OF ARRAY[0]
                return true;
            }
        }
        for(int i =0; i <(node.get("commands").get("get").get("synonyms").size());i++){
            String tempGet = node.get("commands").get("get").get("synonyms").get(i).toString();
            String subTempGet = tempGet.substring(1,tempGet.length()-1);
            if(input[0].equals(subTempGet)){
                System.out.println(input[0] + " :is a synonym for get");
                input[0] = "get";
//                System.out.println(input[0]);  <WAS MAKING SURE IT CHANGED THE VALUE OF ARRAY[0]
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