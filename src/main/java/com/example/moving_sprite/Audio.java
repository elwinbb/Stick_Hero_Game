package com.example.moving_sprite;


import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//THIS WHOLE CLASS IMPLEMENTS FLYWEIGHT DESIGN PATTERN
public class Audio {
    private static Map<String,Audio> audioMAP= new HashMap<String,Audio>();
    private String mediafile;
    private Media media;
    private MediaPlayer mediaPlayer;
    static Audio revive=new Audio("revive.wav");

    public static Audio getaudio(String s){
        if(!audioMAP.containsKey(s)){
            audioMAP.put(s,new Audio(s));
        }
        return audioMAP.get(s);

    }
    public Audio(String filename) {
        String filename2="src/main/resources/com/example/moving_sprite/music/"+filename;
        this.mediafile = filename2;
        String urifilename = new File(filename2).toURI().toString();
        this.media = new Media(urifilename);
        this.mediaPlayer = new MediaPlayer(media);
    }

    public void playaudio(){
        this.mediaPlayer.play();
    }
    public void stop(){
        mediaPlayer.stop();
    }
}

