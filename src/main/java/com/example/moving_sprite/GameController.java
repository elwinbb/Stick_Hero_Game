package com.example.moving_sprite;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class GameController implements Initializable {
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView ninja;
    @FXML
    private Rectangle p1;
    @FXML
    private Rectangle p2;
    @FXML
    private Rectangle stick1;
    @FXML
    private Rectangle stick2;
    @FXML
    private Rectangle bonus;
    @FXML
    private  ImageView cherry;
    @FXML
    private ImageView ShurikenImage;
    @FXML
    private ImageView Cherrylogo;
    @FXML
    private Text scoretext;
    @FXML
    private Text cherrycounter;
    @FXML
    private Text plus_one;
    public File getFile(String fileName){
        return new File(getClass().getResource(fileName).getPath());
    }
    Image c1 = new Image(getFile("Cherry/Cherry-1.png").getAbsolutePath());
    Image img = new Image(getFile("Shuriken/shuriken.png").getAbsolutePath());
    Image sp1 = new Image(getFile("Sprites/Stick_Hero_Ninja-1.png").getAbsolutePath());
    int score = 0;
    int cherry_counter = 0;
    private boolean bool = true;
    private boolean bool11 = true;
    private boolean bool2 = true;
    private final Block b = new Block();
    private final NinjaController ninjaController = new NinjaController();
    private final StickController stickController = new StickController();
    private final Shuriken s = new Shuriken();
    private final List<javafx.scene.Node> objectsToMove = new ArrayList<>();
    private final List<javafx.scene.Node> objectsToMove2 = new ArrayList<>();
    private Timeline GameLoop;
    private Timeline GameLoop2;
    public void ShurikenAndCherryGenerate(Rectangle p){
        if (p.getLayoutX() + p.getWidth()/2 >= 300){
            ShurikenImage.setImage(img);
            ShurikenImage.setX(p.getLayoutX()+p.getWidth()/2 + 12.5);
            ShurikenImage.setRotate(0);
        }
//        System.out.println("layout "+p.getLayoutX());
//        System.out.println("width "+p.getWidth());
//        System.out.println("Shuriken:70 "+ShurikenImage.getLayoutX());
//        System.out.println("Shuriken "+ ShurikenImage.getX());
        cherry.setImage(c1);
        ninjaController.cherryposition = (p.getLayoutX()-50)/2;
        cherry.setX((p.getLayoutX()-50)/2);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.2), cherry);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();
    }
    private void setDefaultValues(Rectangle stick,ImageView ninja,Rectangle p,ImageView shuriken){
        b.setDimensions(p,bonus,shuriken);
        stickController.setvals(stick); // very imp to set the configuration to default
        stick.setLayoutX(98);
        stick.setLayoutY(400);
        stick.setY(0);
        ninja.setX(0);
        ninja.setLayoutX(70);
        shuriken.setLayoutX(-26);
        shuriken.setX(0);
        cherry.setLayoutX(60);
        ShurikenAndCherryGenerate(p);
    }
    //SAMEEEEEE
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        objectsToMove.add(ninja);
        objectsToMove.add(stick1);
        objectsToMove.add(stick2);
        objectsToMove.add(p1);
        objectsToMove.add(p2);
        objectsToMove.add(bonus);
        objectsToMove.add(cherry);
        objectsToMove.add(ShurikenImage);

        objectsToMove2.add(ninja);
        objectsToMove2.add(stick1);
        objectsToMove2.add(stick2);
        objectsToMove2.add(p1);
        objectsToMove2.add(p2);
        objectsToMove2.add(bonus);
        objectsToMove2.add(cherry);
        objectsToMove2.add(ShurikenImage);
        setDefaultValues(stick1,ninja,p2,ShurikenImage);
        stickController.setdefaultbools(stick1);
        ninjaController.setdefault();

        stickController.GrowStick(scene, stick1);
        GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (stickController.StopRotation && ninjaController.alive) { // checks if stick has stopped rotation
                if (p2.getLayoutX() + p2.getWidth()/2 >= 300){
                    s.rotate(ShurikenImage);
                }
                if (bool) {
                    ninjaController.didNinjaLand(stick1, p2);
                    if(ninjaController.checkBonus(stick1,p2)){
                        score++;
                        plus_one.setText("+1");
                        plus_one.setLayoutX(p2.getLayoutX() + p2.getWidth()/2 - 6);
                        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.75), plus_one);
                        translateTransition.setFromY(0);
                        translateTransition.setToY(-35);
                        translateTransition.setCycleCount(1);
                        translateTransition.setAutoReverse(false);
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), plus_one);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1);
                        fadeTransition.setCycleCount(1);
                        fadeTransition.setAutoReverse(false);
                        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(2), plus_one);
                        fadeOutTransition.setFromValue(1);
                        fadeOutTransition.setToValue(0);
                        fadeOutTransition.setCycleCount(1);
                        fadeOutTransition.setAutoReverse(false);
                        translateTransition.play();
                        fadeTransition.play();
                        fadeOutTransition.play();
                    }
                    ninjaController.setdefault();
                    bool = false;
                }
                ninjaController.cherryCollected(cherry);
                if (ninjaController.landed) {
                    ninjaController.MoveNinja(ninja, scene, p2.getLayoutX() + p2.getWidth() - 100, p2,ShurikenImage);
                    if (!ninjaController.ninjamoving && ninjaController.movingended && ninjaController.alive) {
                        stopTimeline();
                        score++;
                        bool2 = true;
                        stickController.setdefaultbools(stick1); //
                        ninja.setImage(sp1);//
                        ninja.setY(0);//
                        ninjaController.setdefault();
                        Thread moveObjectsBackThread = new Thread(this::moveObjectsBack);//
                        moveObjectsBackThread.start();//
                        stickController.GrowStick(scene, stick2); //
                        GameLoop2.play();
                    }
                }
                else if (!bool) {
                    ninjaController.MoveNinja(ninja, scene, stick1.getHeight() + 25, p2,ShurikenImage);
                    if (!ninjaController.ninjamoving && ninjaController.check) {
                        ninjaController.FallNinja(ninja);
                        stickController.StickFall(stick1);
                    }
                }
            }
        }));
        GameLoop.setCycleCount(Timeline.INDEFINITE);
        GameLoop.play();

        //SECOND TIMELINE OF GAME STARTS HERE
        GameLoop2 = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (stickController.StopRotation && ninjaController.alive) { // checks if stick has stopped rotation
                if (p1.getLayoutX() + p1.getWidth()/2 >= 300){
                    s.rotate(ShurikenImage);
                }
                if (bool2) {
                    ninjaController.didNinjaLand(stick2, p1);
                    if(ninjaController.checkBonus(stick2,p1)){
                        score++;
                        plus_one.setText("+1");
                        plus_one.setLayoutX(p1.getLayoutX() + p1.getWidth()/2 - 6);
                        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.75), plus_one);
                        translateTransition.setFromY(0);
                        translateTransition.setToY(-35);
                        translateTransition.setCycleCount(1);
                        translateTransition.setAutoReverse(false);
                        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), plus_one);
                        fadeTransition.setFromValue(0);
                        fadeTransition.setToValue(1);
                        fadeTransition.setCycleCount(1);
                        fadeTransition.setAutoReverse(false);
                        FadeTransition fadeOutTransition = new FadeTransition(Duration.seconds(2), plus_one);
                        fadeOutTransition.setFromValue(1);
                        fadeOutTransition.setToValue(0);
                        fadeOutTransition.setCycleCount(1);
                        fadeOutTransition.setAutoReverse(false);
                        translateTransition.play();
                        fadeTransition.play();
                        fadeOutTransition.play();
                    }
                    bool2 = false;
                    ninjaController.setdefault();
                }
                ninjaController.cherryCollected(cherry);
                if (ninjaController.landed) {
                    ninjaController.MoveNinja(ninja, scene, p1.getLayoutX() + p1.getWidth() - 100, p1,ShurikenImage);

                    if (!ninjaController.ninjamoving && ninjaController.movingended && ninjaController.alive) {
                        stopTimeline2();
                        score++;
                        bool = true;
                        stickController.setdefaultbools(stick2); //
                        ninjaController.setdefault();
                        ninja.setImage(sp1);//
                        ninja.setY(0);//
                        Thread moveObjectsBackThread = new Thread(this::moveObjectsBack2);//
                        moveObjectsBackThread.start();//
                        stickController.GrowStick(scene, stick1); //
                        GameLoop.play();
                    }
                }
                else if (!bool2) {
                    ninjaController.MoveNinja(ninja, scene, stick2.getHeight() + 25, p1,ShurikenImage);
                    if (!ninjaController.ninjamoving && ninjaController.check) {
                        ninjaController.FallNinja(ninja);
                        stickController.StickFall(stick2);
                    }
                }
            }
        }));
        GameLoop2.setCycleCount(Timeline.INDEFINITE);
    }
    private void stopTimeline() {
        GameLoop.stop();
    }
    private void stopobjectsmoving(){
        moveObjectsBack.stop();
    }
    Timeline moveObjectsBack = new Timeline(new KeyFrame(Duration.seconds(0.002), event -> {
        if(bool11){
            ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(0.15), scoretext);
            scaleTransition1.setFromX(1.0);
            scaleTransition1.setFromY(1.0);
            scaleTransition1.setToX(1.5);
            scaleTransition1.setToY(1.5);
            scaleTransition1.setCycleCount(2);
            scaleTransition1.setAutoReverse(true);
            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(0.15), Cherrylogo);
            scaleTransition2.setFromX(1.0);
            scaleTransition2.setFromY(1.0);
            scaleTransition2.setToX(1.5);
            scaleTransition2.setToY(1.5);
            scaleTransition2.setCycleCount(2);
            scaleTransition2.setAutoReverse(true);
            ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(0.15), cherrycounter);
            scaleTransition3.setFromX(1.0);
            scaleTransition3.setFromY(1.0);
            scaleTransition3.setToX(1.5);
            scaleTransition3.setToY(1.5);
            scaleTransition3.setCycleCount(2);
            scaleTransition3.setAutoReverse(true);
            scaleTransition1.play();
            scoretext.setText(""+score); //Updating the score
            if(ninjaController.cherrycollected){
                scaleTransition2.play();
                cherry_counter+=1;
                cherrycounter.setText(""+cherry_counter); //Updating the cherrycount
                scaleTransition3.play();
                ninjaController.cherrycollected = false;
            }
            bool11 = false;
        }
        if ((p2.getLayoutX() + p2.getWidth() > 100)) {
            for (javafx.scene.Node node : objectsToMove) {
                node.setLayoutX(node.getLayoutX() - 1);
            }
            //change thissss
        }
        else{
            p1.setLayoutX(500);
            stopobjectsmoving();
            s.stop();
            bool11 = true;
            setDefaultValues(stick2,ninja,p1,ShurikenImage); //Set all values to original
        }
    }));
    private void moveObjectsBack() {
        moveObjectsBack.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack.play();
    }
    private void stopTimeline2() {
        GameLoop2.stop();
    }
    private void stopobjectsmoving2(){
        moveObjectsBack2.stop();
    }
    Timeline moveObjectsBack2 = new Timeline(new KeyFrame(Duration.seconds(0.002), event -> {
        if(bool11){
            ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(0.15), scoretext);
            scaleTransition1.setFromX(1.0);
            scaleTransition1.setFromY(1.0);
            scaleTransition1.setToX(1.5);
            scaleTransition1.setToY(1.5);
            scaleTransition1.setCycleCount(2);
            scaleTransition1.setAutoReverse(true);
            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(0.15), Cherrylogo);
            scaleTransition2.setFromX(1.0);
            scaleTransition2.setFromY(1.0);
            scaleTransition2.setToX(1.5);
            scaleTransition2.setToY(1.5);
            scaleTransition2.setCycleCount(2);
            scaleTransition2.setAutoReverse(true);
            ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(0.15), cherrycounter);
            scaleTransition3.setFromX(1.0);
            scaleTransition3.setFromY(1.0);
            scaleTransition3.setToX(1.5);
            scaleTransition3.setToY(1.5);
            scaleTransition3.setCycleCount(2);
            scaleTransition3.setAutoReverse(true);
            scaleTransition1.play();
            scoretext.setText(""+score); //Updating the score
            if(ninjaController.cherrycollected){
                scaleTransition2.play();
                cherry_counter+=1;
                cherrycounter.setText(""+cherry_counter); //Updating the cherrycount
                System.out.println("cherry collected");
                scaleTransition3.play();
                ninjaController.cherrycollected = false;
            }
            bool11 = false;
        }
        if ((p1.getLayoutX() + p1.getWidth() > 100)) {
            for (javafx.scene.Node node : objectsToMove2) {
                node.setLayoutX(node.getLayoutX() - 1);
            }
        }
        else{
            p2.setLayoutX(500);
            stopobjectsmoving2();
            s.stop();
            bool11 = true;
//            System.out.println(p1.getLayoutX()+p1.getWidth());
//            System.out.println(ninja.getX() + ninja.getLayoutX());
            setDefaultValues(stick1,ninja,p2,ShurikenImage);
        }
    }));
    private void moveObjectsBack2() {
        moveObjectsBack2.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack2.play();
    }
}

// STUFF LEFT TO DO :
// IF U HAVE TIME MAKE THE RANDOM GENERATION OF THE CHERRY AS WELL(TO THE END OR AT THE START)
// MAKE THE MAIN GAME LOOP TO REPEAT BY ADDING ANOTHER TIMELINE WHICH IS CALLED IN THE FIRST ONE, RIGHT AFTER IT FINISHES. REMEMBER
// TO ADD THE REVIVE FEATURE WHILE DOING THIS.
// THE SECOND TIMELINE SHOULD SET ALL THE VALUES TO THEIR ORIGINAL STATE. P1 -> P2. S1 -> S2. P2 -> P1. ALSO SET THE
// GETLAYOUTX AND GETX OF EVERY OTHER NODE TO HOW YOU WANT IT IN REFERNCE TO THE SCENENUILDER
// TRY TO IMPLEMENT THE USE OF THREADS BY CALLING THE STARTTIMELINE METHOD IN EACH THREAD, SHOULDNT BE VEY HARD.

