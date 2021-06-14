package com.infected.util;
import com.infected.model.Location;
import com.infected.model.Player;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    public static void playSound(String path){
        final JFXPanel fxPanel = new JFXPanel();
        String sound = path ;
        Media hit = new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setAutoPlay(true);
    }
    public static void playWalkSound(){
        String path = "sounds/walk.mp3";
        for(int i=0; i<3;i++){
            MusicPlayer.playSound(path);
            Pause.pause(400);
        }
    }
    public static void playDoorSound(){
        String path = "sounds/door.mp3";
        MusicPlayer.playSound(path);
    }
    public static void playCoughSound(){
        String path = "sounds/sneeze.mp3";
        MusicPlayer.playSound(path);
    }
    public static void playHospitalSound(){
        String path = "sounds/hospital.mp3";
        MusicPlayer.playSound(path);
    }
    public static void startSound(){
        String path = "sounds/new.mp3";
        MusicPlayer.playSound(path);
    }
    public static void loseSound(){
        String path = "sounds/lose.mp3";
        MusicPlayer.playSound(path);
    }
    public static void winSound(){
        String path = "sounds/win.mp3";
        MusicPlayer.playSound(path);
    }
    public static void main(String[]args){
        MusicPlayer.loseSound();
        Pause.pause(5000);
        System.exit(0);
    }

}