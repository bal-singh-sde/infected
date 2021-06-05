package com.infected.model;

public class Player {
    private static String currentLocation = "home";

    public static String getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(String currentLocation) {
        Player.currentLocation = currentLocation;
    }
}
