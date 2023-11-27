package com.example.moving_sprite;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;

public class Shuriken{
    @FXML
    ImageView sh;
    public File getFile(String fileName){
        return new File(getClass().getResource(fileName).getPath());
    }
    Image img = new Image(getFile("Shuriken/shuriken.png").getAbsolutePath());
    double angle = 0;
    int distance = 0;
    public void rotate(ImageView sh){
        this.sh = sh;
        sh.setImage(img);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
        if (distance < 400){
            sh.setRotate(angle);
            sh.setX(sh.getX() - 1);
            angle -= 5;
        }
        else{
            stop();
        }
    }));
    public void stop(){
        timeline.stop();
    }
}