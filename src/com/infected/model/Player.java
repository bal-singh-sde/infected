package com.infected.model;

public class Player {
    private static String currentLocation = "home";
	private static int contaminationLevel =5;

    public static String getCurrentLocation() {
        return currentLocation;
    }
    public static int getContaminationLevel(){
        return contaminationLevel;
    }
    public static void setCurrentLocation(String currentLocation) {
        Player.currentLocation = currentLocation;
    }
    public static void quarantine(){
        if(Player.getCurrentLocation().equals("home") && contaminationLevel !=0){
            contaminationLevel = 0;
            System.out.println("CONTAMINATION LEVEL: "+ Player.getContaminationLevel());
            System.out.println("\n1: go west \n2: quit");
        }else if(!Player.getCurrentLocation().equals("home")){
            System.out.println("MUST BE AT LOCATION HOME TO QUARANTINE");
            Navigation.routes();

        } else{
            System.out.println("CONTAMINATION LEVEL IS ALREADY AT: 0");
            Navigation.routes();
        }
    }

}
