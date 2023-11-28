package com.example.moving_sprite;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;

import java.io.File;

public class Cherry {
    int counter=0;
    double position;
    boolean collected = false;
    public Cherry(double position,ImageView c){
        this.position = position; //position is with respect to the ninja because they have the same layoutX = 60
        c.setX(position);
    }
    public File getFile(String fileName){
        return new File(getClass().getResource(fileName).getPath());
    }
    Image c1 = new Image(getFile("Cherry/Cherry-1.png").getAbsolutePath());
    public void cherryCollected(NinjaController ninjaController,ImageView ninja,ImageView cherry) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), cherry);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), cherry);
            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(0);
            scaleTransition.setToY(0);
            scaleTransition.setCycleCount(1);
            scaleTransition.setAutoReverse(false);
        if (!this.collected && ninjaController.reversed.get() && ((ninja.getX() <= position && position <= ninja.getX()+25) || (ninja.getX() <= position+cherry.getFitWidth() && position+cherry.getFitWidth() <= ninja.getX()+25) )){
            //fadeTransition.play();
            scaleTransition.play();
            counter+=1;
            this.collected = true;
        }
    }
}
