package com.example.moving_sprite;


import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

import java.io.File;

public class Audio {
    private String mediafile;
    private Media media;
    private MediaPlayer mediaPlayer;
    static Audio stickgrow=new Audio("src/main/resources/com/example/moving_sprite/music/stickgrow.wav");
    static Audio splat=new Audio("src/main/resources/com/example/moving_sprite/music/SPLAT.wav");
    static Audio swoosh=new Audio("src/main/resources/com/example/moving_sprite/music/SWOOSH.wav");
    static Audio bg=new Audio("src/main/resources/com/example/moving_sprite/music/bgmusic.wav");
    static Audio basic=new Audio("src/main/resources/com/example/moving_sprite/music/basic.wav");
    static Audio cherrycollect=new Audio("src/main/resources/com/example/moving_sprite/music/pop.wav");
    static Audio stickfall=new Audio("src/main/resources/com/example/moving_sprite/music/stickfall.wav");
    static Audio bonus=new Audio("src/main/resources/com/example/moving_sprite/music/bonus.wav");


    public Audio(String filename) {
        this.mediafile = filename;
        String urifilename = new File(filename).toURI().toString();
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

