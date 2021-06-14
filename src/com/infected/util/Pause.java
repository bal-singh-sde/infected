package com.infected.util;

public class Pause {
    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}