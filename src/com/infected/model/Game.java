package com.infected.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.infected.util.TextParser;

import java.io.File;
import java.io.IOException;

public class Game {
    private static final File jsonSource = new File("./data/Game.json");
    private static final File cmdSource = new File("./data/Cmd.json");

    private static JsonNode getGameNode() {
        try {
            return TextParser.parse(jsonSource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static JsonNode getCmdNode() {
        try {
            return TextParser.parse(cmdSource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static boolean isGameSaved() {
        try {
            return TextParser.parse(jsonSource).get("isGameSaved").asBoolean();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return false;
    }

    public static void saveGame() {
        if (!isGameSaved()) {
            JsonNode newGameNode = TextParser.getNewNode(getGameNode(), "isGameSaved", true);
            try {
                TextParser.write(jsonSource, newGameNode);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void clearGameData() {
        Player.resetPlayer();
        resetGame();
    }

    private static void resetGame() {
        JsonNode newGameNode = TextParser.getNewNode(getGameNode(), "isGameSaved", false);
        try {
            TextParser.write(jsonSource, newGameNode);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}