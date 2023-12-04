package com.example.moving_sprite;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class NinjaController extends Ninja{

    public final BooleanProperty reversed = new SimpleBooleanProperty();
    public boolean movingended = false;
    public void setdefault(){
        Spritenumber = 1;
        x = 0;
        y = 0;
        speed = 1;
        angle = 0;
        check = false;
        movingended = false;
        alive = true;
        reversed.setValue(false);
    }
    @FXML
    private ImageView runner;
    @FXML
    private AnchorPane scene;
    @FXML
    private Rectangle p;
    @FXML
    private ImageView shuriken;

    boolean ninjamoving;
    double Distance;
    boolean check;
    boolean cherrycollected;
    double cherryposition;

    public void MoveNinja(ImageView runner, AnchorPane scene,double Distance,Rectangle p,ImageView shuriken){
        this.runner = runner;
        this.scene = scene;
        this.Distance = Distance;
        this.p = p;
        this.shuriken = shuriken;
        movementSetup();
        timeline.setCycleCount(Animation.INDEFINITE);
        startRunning();
    }
    private void movementSetup(){
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && ninjamoving) {
                Audio.swoosh.stop();
                Audio.swoosh.playaudio();
                reversed.setValue(!reversed.get());
            }
        });
    }

    //RUNNING ANIMATION
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
        didNinjaCollide();
        didNinjaCollideAfterLanding();
        ShurikenHit(shuriken);
        if (runner.getX() < Distance && this.alive) {
            ninjamoving = true;
            if (!reversed.get()) {
                runner.setY(0);
                if ((int) Spritenumber % 4 == 1) {
                    runner.setImage(sp1);
                }
                if ((int) Spritenumber % 4 == 2) {
                    runner.setImage(sp2);
                }
                if ((int) Spritenumber % 4 == 3) {
                    runner.setImage(sp3);
                }
                if ((int) Spritenumber % 4 == 0) {
                    runner.setImage(sp4);
                }
            } else if(reversed.get() && !((runner.getX() + runner.getLayoutX() + 25 <= p.getLayoutX() + p.getWidth() && runner.getX() + runner.getLayoutX() + 25 >= p.getLayoutX()) || (runner.getX() + runner.getLayoutX() <= p.getLayoutX() + p.getWidth() && runner.getX() + runner.getLayoutX() >= p.getLayoutX()))) {
                runner.setY(27);
                if ((int) Spritenumber % 4 == 1) {
                    runner.setImage(sp_1);
                }
                if ((int) Spritenumber % 4 == 2) {
                    runner.setImage(sp_2);
                }
                if ((int) Spritenumber % 4 == 3) {
                    runner.setImage(sp_3);
                }
                if ((int) Spritenumber % 4 == 0) {
                    runner.setImage(sp_4);
                }
            }
            Spritenumber += 0.10;
            x = x + speed;
            runner.setX(x);
            check = true;// this is used so that it does not start the falling animation in gamecontroller
        }
        else{
            stopRunning();
            ninjamoving = false;
        }
    }));

    public void startRunning(){
        timeline.play();
    }

    public void stopRunning(){
        timeline.stop();
        movingended = true;
    }
    double inc = 0.5;
    Timeline dying = new Timeline(new KeyFrame(Duration.seconds(0.0025), event -> {
        runner.setX(x);
        runner.setY(y);
        runner.setRotate(angle);
        if (runner.getY() < 200) {
            y = y + 1;
            angle += 1;
            //x+=0.3;
            inc+=0.01;
        }
        else{
            inc =0.5;
            stopFalling();
        }
    }));

    public void startFalling(){
        Audio.splat.stop();
        Audio.splat.playaudio();
        dying.play();
    }

    public void stopFalling(){
        dying.stop();
    }
    public void FallNinja(ImageView runner){
        this.runner = runner;
        runner.setImage(ded);
        dying.setCycleCount(Animation.INDEFINITE);
        startFalling();
    }
    public void didNinjaLand(Rectangle stick, Rectangle p){
        double s = stick.getHeight() + 100; //100 is where the stick starts to grow from
        this.landed =  ((p.getLayoutX()) < s) && s < (p.getLayoutX() + p.getWidth());
    }
    public void didNinjaCollideAfterLanding(){
        if((runner.getX() + runner.getLayoutX() + 25 == p.getLayoutX()) && reversed.get() && landed){
            this.alive = false;
            System.out.println("Dead");
            FallNinja(runner);
            stopRunning();
            Audio.splat.stop();
            Audio.splat.playaudio();
        }
    }
    public void ShurikenHit(ImageView shuriken) {
        if (!this.reversed.get() && ((runner.getX() + runner.getLayoutX() <= shuriken.getX() + shuriken.getLayoutX() && shuriken.getX() + shuriken.getLayoutX() <= runner.getX()+ runner.getLayoutX()+25) || (runner.getX() + runner.getLayoutX() <= shuriken.getX()+ shuriken.getLayoutX() +shuriken.getFitWidth() && shuriken.getX()+ shuriken.getLayoutX()+shuriken.getFitWidth() <= runner.getX()+ runner.getLayoutX()+25))){
            this.alive = false;
            System.out.println("Dead");
            FallNinja(runner);
            stopRunning();
        }
    }
    public void didNinjaCollide(){
        if(((runner.getX() + runner.getLayoutX() + 25 <= p.getLayoutX() + p.getWidth() && runner.getX() + runner.getLayoutX() + 25 >= p.getLayoutX()) || (runner.getX() + runner.getLayoutX() <= p.getLayoutX() + p.getWidth() && runner.getX() + runner.getLayoutX() >= p.getLayoutX())) && reversed.get() && !landed && ninjamoving){
            this.alive = false;
            System.out.println("Dead");
            FallNinja(runner);
            stopRunning();
        }
    }
    public void cherryCollected(ImageView cherry) {
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
        if (!cherrycollected && reversed.get() && ((runner.getX() <= cherryposition && cherryposition <= runner.getX()+25) || (runner.getX() <= cherryposition + cherry.getFitWidth() && cherryposition + cherry.getFitWidth() <= runner.getX()+25) )){
            //fadeTransition.play();
            scaleTransition.play();
            cherrycollected = true;
            Audio.cherrycollect.stop();
            Audio.cherrycollect.playaudio();
        }
    }
    public boolean checkBonus(Rectangle stick, Rectangle p){
        double bonusx = p.getLayoutX() + p.getWidth()/2 - 6;
        double s = stick.getHeight() + 100; //100 is where the stick starts to grow from
        return (s > bonusx && s < bonusx+12);
    }
}

