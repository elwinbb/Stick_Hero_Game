package com.example.moving_sprite;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private AnchorPane scene;
    @FXML
    private ImageView ninja;
    @FXML
    private Rectangle stick1;
    @FXML
    private Rectangle p1;
    @FXML
    private Rectangle p2;
    @FXML
    private Rectangle stick2;
    @FXML
    private Rectangle bonus;
    @FXML
    private  ImageView cherry;
    @FXML
    private ImageView ShurikenImage;
    @FXML
    private Text score;
    @FXML
    private Text cherrycounter;
    public File getFile(String fileName){
        return new File(getClass().getResource(fileName).getPath());
    }
    Image img = new Image(getFile("Shuriken/shuriken.png").getAbsolutePath());
    Image sp1 = new Image(getFile("Sprites/Stick_Hero_Ninja-1.png").getAbsolutePath());
    Image empty = new Image(getFile("emptyimage.png").getAbsolutePath());
    boolean bool = true;
    private Block b = new Block();
    private final NinjaController ninjaController = new NinjaController();
    private final StickController stickController = new StickController();
    private final Shuriken s = new Shuriken();
    private final List<javafx.scene.Node> objectsToMove = new ArrayList<>();
    private Cherry c;
    private Timeline GameLoop;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        objectsToMove.add(ninja);
        objectsToMove.add(stick1);
        objectsToMove.add(p1);
        objectsToMove.add(p2);
        objectsToMove.add(bonus);
        objectsToMove.add(cherry);
        objectsToMove.add(ShurikenImage);
        //setDefaultValues(stick1);
        b.setDimensions(p2,bonus);
        ShurikenImage.setImage(empty);
        ShurikenAndCherryGenerate(p2);
        //MAIN GAME STARTS HERE
        score.setText("0");
        stickController.GrowStick(scene, stick1);
        GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (stickController.StopRotation && ninjaController.alive) { // checks if stick has stopped rotation
                s.rotate(ShurikenImage);
                if (bool) {
                    ninjaController.didNinjaLand(stick1, p2);
                    System.out.println(ninjaController.landed);
                    bool = false;
                }
                c.cherryCollected(ninjaController, ninja, cherry);
                if (ninjaController.landed) {
                    ninjaController.MoveNinja(ninja, scene, p2.getLayoutX() + p2.getWidth() - 100, p2,ShurikenImage);
                    if (!ninjaController.ninjamoving && ninjaController.movingended && ninjaController.alive) {
                        ninja.setImage(sp1);
                        ninja.setY(0);
                        stopTimeline();
                        Thread moveObjectsBackThread = new Thread(this::moveObjectsBack);
                        moveObjectsBackThread.start();
                        score.setText("1");
                        cherrycounter.setText(""+c.counter);
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
    }
    private void stopTimeline() {
        GameLoop.stop();
    }
    private void stopobjectsmoving(){
        moveObjectsBack.stop();
    }
    Timeline moveObjectsBack = new Timeline(new KeyFrame(Duration.seconds(0.001), event -> {
        if ((p2.getLayoutX() + p2.getWidth() > 100)) {
            for (javafx.scene.Node node : objectsToMove) {
                node.setLayoutX(node.getLayoutX() - 1);
            }
        }
        else{
            stopobjectsmoving();
            s.stop();
            System.out.println(p2.getLayoutX()+p2.getWidth());
            System.out.println(ninja.getX() + ninja.getLayoutX());
        }

    }));
    private void moveObjectsBack() {
        moveObjectsBack.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack.play();
    }
    public void setDefaultValues(Rectangle stick,ImageView ninja,Rectangle p,ImageView shuriken){
        stick.setLayoutX(98);
    }
    public void ShurikenAndCherryGenerate(Rectangle p){
        if (p.getLayoutX() >= 400){
            ShurikenImage.setImage(img);
            ShurikenImage.setX(p.getLayoutX()+p.getWidth()/2 + 12.5);
        }
        System.out.println(p.getLayoutX());
        c = new Cherry((p.getLayoutX()-50)/2,cherry);
    }
}

// STUFF LEFT TO DO :
// MAKE THE RANDOM GENERATION OF BLOCKS
// IF U HAVE TIME MAKE THE RANDOM GENERATION OF THE CHERRY AS WELL(TO THE END OR AT THE START)
// MAKE THE MAIN GAME LOOP TO REPEAT BY ADDING ANOTHER TIMELINE WHICH IS CALLED IN THE FIRST ONE, RIGHT AFTER IT FINISHES. REMEMBER
// TO ADD THE REVIVE FEATURE WHILE DOING THIS.
// THE SECOND TIMELINE SHOULD SET ALL THE VALUES TO THEIR ORIGINAL STATE. P1 -> P2. S1 -> S2. P2 -> P1. ALSO SET THE
// GETLAYOUTX AND GETX OF EVERY OTHER NODE TO HOW YOU WANT IT IN REFERNCE TO THE SCENENUILDER
// TRY TO IMPLEMENT THE USE OF THREADS BY CALLING THE STARTTIMELINE METHOD IN EACH THREAD, SHOULDNT BE VEY HARD.