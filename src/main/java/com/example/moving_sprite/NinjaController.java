package com.example.moving_sprite;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    public final BooleanProperty dead = new SimpleBooleanProperty();
    public boolean movingended = false;
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
    boolean check = false;

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
            if (e.getCode() == KeyCode.DOWN) {
                reversed.setValue(true);
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.DOWN) {
                reversed.setValue(false);
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
            } else if(reversed.get() && !((runner.getX() + runner.getLayoutX() <= p.getLayoutX() && p.getLayoutX() <= runner.getX()+ runner.getLayoutX() + 25) || (runner.getX() + runner.getLayoutX() <= p.getLayoutX() + p.getWidth() && p.getLayoutX()+p.getWidth() <= runner.getX()+ runner.getLayoutX()+ 25))) {
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

    Timeline dying = new Timeline(new KeyFrame(Duration.seconds(0.0025), event -> {
        runner.setY(y);
        runner.setRotate(angle);
        if (runner.getY() < 200) {
            y = y + 1;
            angle += 1;
        }
        else{
            stopFalling();
        }
    }));

    public void startFalling(){
        dying.play();
    }

    public void stopFalling(){
        dying.stop();
    }
    public boolean isDead(ImageView runner) {
        return 225 > runner.getY();
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
        }
    }
    public void ShurikenHit(ImageView shuriken) {
        if (!this.reversed.get() && ((runner.getX() + runner.getLayoutX() <= shuriken.getX() + shuriken.getLayoutX() && shuriken.getX() + shuriken.getLayoutX() <= runner.getX()+ runner.getLayoutX()+25) || (runner.getX() + runner.getLayoutX() <= shuriken.getX()+ shuriken.getLayoutX() +shuriken.getFitWidth() && shuriken.getX()+ shuriken.getLayoutX()+shuriken.getFitWidth() <= runner.getX()+ runner.getLayoutX()+25) )){
            this.alive = false;
            System.out.println("Dead");
            FallNinja(runner);
            stopRunning();
        }
    }
    public void didNinjaCollide(){
        if(((runner.getX() + runner.getLayoutX() <= p.getLayoutX() && p.getLayoutX() <= runner.getX()+ runner.getLayoutX() + 25) || (runner.getX() + runner.getLayoutX() <= p.getLayoutX() + p.getWidth() && p.getLayoutX()+p.getWidth() <= runner.getX()+ runner.getLayoutX()+ 25)) && reversed.get() && !landed){
            this.alive = false;
            System.out.println("Dead");
            FallNinja(runner);
            stopRunning();
        }
    }
    public void didNinjaHitShuriken(ImageView runner, ImageView shuriken){
    }
}