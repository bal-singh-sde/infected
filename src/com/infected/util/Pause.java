package com.infected.util;

public class Pause {
    public static void pause() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}