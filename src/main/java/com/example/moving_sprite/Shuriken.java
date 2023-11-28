package com.example.moving_sprite;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
        distance = 0;
        timeline.play();
    }
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
        sh.setRotate(angle);
        sh.setX(sh.getX() - 1);
        angle -= 5;
        distance+=1;
    }));
    public void stop(){
        timeline.stop();
    }
}

