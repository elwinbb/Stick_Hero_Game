package com.example.moving_sprite;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class StickController extends Stick{
    Boolean check = false;
    Boolean spacePressed;
    Boolean StopRotation;
    Boolean bool;
    Boolean firstpress = false;
    private int sound_count;

    public void setdefaultbools(Rectangle stick){
        spacePressed = false;
        StopRotation = false;
        bool = false;
        firstpress = false;
        angle = 0;
        height = 0;
    }
    public void setvals(Rectangle stick){
        stick.getTransforms().setAll(new javafx.scene.transform.Rotate(0, stick.getX() + stick.getWidth() / 2.0, stick.getY() + stick.getHeight()));
        stick.setHeight(0);
    }

    @FXML private Rectangle stick;
    @FXML AnchorPane scene;

    public void GrowStick(AnchorPane scene, Rectangle stick){
        this.scene = scene;
        this.stick = stick;
        spacepressed();
        timeline.setCycleCount(Animation.INDEFINITE);
        startGrowing();
    }

    private void spacepressed(){
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && check) {
                Audio stickgrow=Audio.getaudio("stickgrow.wav");
                stickgrow.playaudio();
                spacePressed = true;
                firstpress = true;
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE && check) {
                spacePressed = false;
            }
            if(firstpress) {
                bool = true;
            }
        });
    }

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.0028), event -> {
        if (spacePressed && !bool) {
            if (height < 395) {
                stick.setY(stick.getY() - 1.0);
                height += 1.0;
                stick.setHeight(height);
            }
        }
        else if(bool){
            Audio stickgrow=Audio.getaudio("stickgrow.wav");
            stickgrow.stop();
            if (angle <= 90) {
                Audio stickfall=Audio.getaudio("stickfall.wav");
                stickfall.playaudio();
                double pivotX = stick.getX() + stick.getWidth() / 2.0;
                double pivotY = stick.getY() + stick.getHeight();
                stick.getTransforms().clear();
                stick.getTransforms().add(new javafx.scene.transform.Rotate(angle, pivotX, pivotY));
                angle += 1;
                if(angle>90){
                    StopRotation = true;
                }
            }
            else if(StopRotation){
                Audio stickfall=Audio.getaudio("stickfall.wav");
                stickfall.stop();
                stopRunning();
                check = false;
            }
        }
    }));
    public void startGrowing(){
        timeline.play();
    }
    public void stopRunning(){
        timeline.stop();
    }

}
