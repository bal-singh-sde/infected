package com.infected.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.File;
import java.io.IOException;

import static com.infected.model.Game.isGameSaved;

public class Animation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void turnOffAnimation() {
        if (!isGameSaved()) {
            JsonNode newGameNode = TextParser.getNewNode(TextParser.getNode("./data/Animation.json"), "animation", false);
            try {
                TextParser.write(new File("./data/Animation.json"), newGameNode);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    public static void resetAnimation() {
        JsonNode newAnimationNode = TextParser.getNode("./data/default/Animation.json");
        try {
            TextParser.write(new File("./data/Animation.json"), newAnimationNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newPrint(String s) {
        if (TextParser.getNode("./data/Animation.json").get("animation").asBoolean()) {
            int i;
            for(i = 0; i < s.length(); i++){
                System.out.printf("%c", s.charAt(i));
                try{
                    Thread.sleep(30);//0.5s pause between characters
                }catch(InterruptedException ex){
                    Thread.currentThread().interrupt();
                }
            }
        } else {
            System.out.println(s);
        }
        System.out.println();
    }

    private static int getMaxLength(String... strings) {
        int len = Integer.MIN_VALUE;
        for (String str : strings) {
            len = Math.max(str.length(), len);
        }
        return len;
    }

    private static String padString(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        return sb.append(fill(' ', len - str.length())).toString();
    }

    private static String fill(char ch, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void printBox(String... strings) {
        int maxBoxWidth = getMaxLength(strings);
        String line = "+" + fill('-', maxBoxWidth + 2) + "+";
        System.out.println(line);
        for (String str : strings) {
            System.out.printf("| %s |%n", padString(str, maxBoxWidth));
        }
        System.out.println(line);
    }

    public static void printYellow(String s) {
        System.out.print(ANSI_YELLOW + s + ANSI_RESET);
    }
}
