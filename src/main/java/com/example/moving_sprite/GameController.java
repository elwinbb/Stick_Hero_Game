package com.example.moving_sprite;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    private Pane revivebutton1;
    @FXML
    private Pane revivebutton11;
    @FXML
    private Pane cancel;
    @FXML
    private Text plus_one;

    public GameController() throws IOException, ClassNotFoundException {
    }

    public File getFile(String fileName){
        return new File(Objects.requireNonNull(getClass().getResource(fileName)).getPath());
    }
    Image c1 = new Image(getFile("Cherry/Cherry-1.png").getAbsolutePath());
    Image img = new Image(getFile("Shuriken/shuriken.png").getAbsolutePath());
    Image sp1 = new Image(getFile("Sprites/Stick_Hero_Ninja-1.png").getAbsolutePath());

    private final Score score1=Score.loadscore();

    private int cherry_counter = score1.getCherrycount();
    private int highscore=score1.getHighscore();
    private int score = 0;
    private boolean bool = true;
    private boolean bool11 = true;
    private boolean bool2 = true;
    private final Block b =Block.getinstance();
    private final SceneController sceneController= new SceneController();
    private final NinjaController ninjaController = new NinjaController();
    private final StickController stickController = new StickController();
    private final Shuriken s = new Shuriken();
    private final List<javafx.scene.Node> objectsToMove = new ArrayList<>();
    private final List<javafx.scene.Node> objectsToMove2 = new ArrayList<>();
    private Timeline GameLoop;
    private Timeline GameLoop2;

    public int getScore() {
        return score;
    }

    public void cancel() throws IOException {
        stopTimeline();
        stopTimeline2();
        if(score>highscore){
            highscore=score;
            score1.setHighscore(score);
        }
        score1.setScore(score);
        score1.setCherrycount(cherry_counter);
        score1.savescore();
        sceneController.switchtogameover(ninja);
    }

    public void Reviveno(MouseEvent e) throws IOException {
        cancel();
    }

    public void ShurikenAndCherryGenerate(Rectangle p){
        Random random = new Random();
        Random r1 = new Random();
        int r = r1.nextInt();
        Random r2 = new Random();
        int r_ = r2.nextInt();
        if (r % 2 ==0){
            ShurikenImage.setImage(img);
            ShurikenImage.setX(p.getLayoutX()+p.getWidth()/2 + 12.5);
            ShurikenImage.setRotate(0);
        }
        cherry.setImage(c1);
        double lowerBound = 30;
        double upperBound = Math.max(0, p.getLayoutX() - 100);
        int randomPosition = random.nextInt((int) (upperBound - lowerBound)) + (int) lowerBound;
        ninjaController.cherryposition = randomPosition;
        cherry.setX(randomPosition);
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
        Audio basic=Audio.getaudio("basic.wav");
        basic.stop();
        Audio cherrycollect=Audio.getaudio("pop.wav");
        cherrycollect.stop();
        Audio bonusaudio = Audio.getaudio("bonus.mp3");
        bonusaudio.stop();
        Audio swoosh=Audio.getaudio("SWOOSH.wav");
        swoosh.playaudio();
        stickController.check = true;
        b.setDimensions(p,bonus,shuriken);
        stickController.setvals(stick);
        ninjaController.alive = true;
        stick.setLayoutX(98);
        stick.setLayoutY(400);
        stick.setY(0);
        ninja.setX(0);
        ninja.setLayoutX(70);
        ninja.setImage(sp1);
        ninja.setRotate(0);
        shuriken.setLayoutX(-26);
        shuriken.setX(0);
        cherry.setLayoutX(70);
        ShurikenAndCherryGenerate(p);
    }
    public void revivepressed1(javafx.scene.input.MouseEvent e){
        cherry_counter -= 10;
        cherrycounter.setText(""+cherry_counter);
        revivebutton1.setLayoutX(-152);
        cancel.setLayoutX(-152);
        sceneController.unblurscreen(scene);
        ninjaController.cherrycollected = false;
        stickController.setdefaultbools(stick1);
        stopTimeline();
        ninja.setImage(sp1);
        ninja.setRotate(0);
        Thread moveObjectsBackThreadAfterDeath = new Thread(this::moveObjectsBack_1);
        moveObjectsBackThreadAfterDeath.start();
        try {
            moveObjectsBackThreadAfterDeath.join(); // MULTITHREADING HAS BEEN USED HERE
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        Thread reviveNinjaThread = new Thread(this::reviveninja1);
        reviveNinjaThread.start();
        try {
            moveObjectsBackThreadAfterDeath.join();
            reviveNinjaThread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        ninjaController.revivebool = false;
    }
    public void reviveninja1(){
        FadeTransition fadein = new FadeTransition(Duration.seconds(1.3), ninja);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);
        fadein.setAutoReverse(false);
        ninja.setLayoutX(70);
        ninja.setLayoutY(375);
        ninja.setX(0);
        ninja.setY(0);
        fadein.play();
        Audio.revive.playaudio();
        ninjaController.setdefault();
        stickController.GrowStick(scene, stick2);
        bool2 = true;
        GameLoop2.play();
    }
    public void revivepressed2(){
        cherry_counter -= 10;
        cherrycounter.setText(""+cherry_counter);
        revivebutton11.setLayoutX(-152);
        cancel.setLayoutX(-152);
        sceneController.unblurscreen(scene);
        ninjaController.cherrycollected = false;
        stickController.setdefaultbools(stick2);
        stopTimeline2();
        ninja.setImage(sp1);
        ninja.setRotate(0);
        Thread moveObjectsBackThreadAfterDeath = new Thread(this::moveObjectsBack_2);
        moveObjectsBackThreadAfterDeath.start();
        try {
            moveObjectsBackThreadAfterDeath.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        Thread reviveNinjaThread = new Thread(this::reviveninja2);
        reviveNinjaThread.start();
        try {
            moveObjectsBackThreadAfterDeath.join();
            reviveNinjaThread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        ninjaController.revivebool = false;

    }
    public void reviveninja2(){
        FadeTransition fadein = new FadeTransition(Duration.seconds(1.3), ninja);
        fadein.setFromValue(0);
        fadein.setToValue(1);
        fadein.setCycleCount(1);
        fadein.setAutoReverse(false);
        ninja.setLayoutX(70);
        ninja.setLayoutY(375);
        ninja.setX(0);
        ninja.setY(0);
        fadein.play();
        Audio.revive.playaudio();
        ninjaController.setdefault();
        stickController.GrowStick(scene, stick1);
        bool = true;
        GameLoop.play();
    }
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
        cherrycounter.setText(String.valueOf(cherry_counter));
        GameLoop = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (stickController.StopRotation && ninjaController.alive) {
                if ((p2.getLayoutX() + p2.getWidth()/2 >= 300 && p2.getWidth() <= 125) || (p2.getWidth()<=75 && p2.getLayoutX() >= 200)){
                    s.rotate(ShurikenImage);
                }
                if (bool) {
                    Audio.revive.stop();
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
                        //Animation for text
                        Audio bonus = Audio.getaudio("bonus.mp3");
                        bonus.playaudio();
                        translateTransition.play();
                        fadeTransition.play();
                        fadeOutTransition.play();
                    }
                    ninjaController.setdefault();
                    bool = false;
                }
                ninjaController.cherryCollected(cherry);
                if (ninjaController.landed) {
                    ninjaController.MoveNinja(ninja, scene, p2.getLayoutX() + p2.getWidth() - 100, p2,ShurikenImage,stick1);
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
                        Audio basic=Audio.getaudio("basic.wav");
                        basic.playaudio();
                        GameLoop2.play();
                    }
                }
                else if (!bool) {
                    ninjaController.MoveNinja(ninja, scene, stick1.getHeight() + 25, p2,ShurikenImage,stick1);
                    if (!ninjaController.ninjamoving && ninjaController.check) {
                        ninjaController.FallNinja(ninja);
                    }
                }
            }
            else if(ninjaController.revivebool){
                if (cherry_counter >= 10)
                {
                    revivebutton1.setLayoutX(181);
                    revivebutton1.setLayoutY(192);
                    cancel.setLayoutX(191);
                    cancel.setLayoutY(288);
                    sceneController.blurscreen(scene);
                }
                else{
                    try {
                        cancel();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
        GameLoop.setCycleCount(Timeline.INDEFINITE);
        GameLoop.play();

        GameLoop2 = new Timeline(new KeyFrame(Duration.seconds(0.005), event -> {
            if (stickController.StopRotation && ninjaController.alive) {
                if ((p1.getLayoutX() + p1.getWidth()/2 >= 300 && p1.getWidth() <= 125) || (p1.getWidth()<=75 && p1.getLayoutX() >= 200)){
                    s.rotate(ShurikenImage);
                }
                if (bool2) {
                    Audio.revive.stop();
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
                        //Animation for text
                        Audio bonus = Audio.getaudio("bonus.mp3");
                        bonus.playaudio();
                        translateTransition.play();
                        fadeTransition.play();
                        fadeOutTransition.play();
                    }
                    ninjaController.setdefault();
                    bool2 = false;
                }
                ninjaController.cherryCollected(cherry);
                if (ninjaController.landed) {
                    ninjaController.MoveNinja(ninja, scene, p1.getLayoutX() + p1.getWidth() - 100, p1,ShurikenImage,stick2);
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
                        Audio basic=Audio.getaudio("basic.wav");
                        basic.playaudio();
                        GameLoop.play();
                    }
                }
                else if (!bool2) {
                    ninjaController.MoveNinja(ninja, scene, stick2.getHeight() + 25, p1,ShurikenImage,stick2);
                    if (!ninjaController.ninjamoving && ninjaController.check) {
                        ninjaController.FallNinja(ninja);
                        ninjaController.revivebool = true;
                    }
                }
            }
            else if(ninjaController.revivebool){
                if (cherry_counter >= 10)
                {
                    revivebutton11.setLayoutX(181);
                    revivebutton11.setLayoutY(192);
                    cancel.setLayoutX(191);
                    cancel.setLayoutY(288);
                    sceneController.blurscreen(scene);
                }
                else{
                    try {
                        cancel();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
            scaleTransition1.setToX(1.3);
            scaleTransition1.setToY(1.3);
            scaleTransition1.setCycleCount(2);
            scaleTransition1.setAutoReverse(true);
            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(0.15), Cherrylogo);
            scaleTransition2.setFromX(1.0);
            scaleTransition2.setFromY(1.0);
            scaleTransition2.setToX(1.3);
            scaleTransition2.setToY(1.3);
            scaleTransition2.setCycleCount(2);
            scaleTransition2.setAutoReverse(true);
            ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(0.15), cherrycounter);
            scaleTransition3.setFromX(1.0);
            scaleTransition3.setFromY(1.0);
            scaleTransition3.setToX(1.3);
            scaleTransition3.setToY(1.3);
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
            scaleTransition1.setToX(1.3);
            scaleTransition1.setToY(1.3);
            scaleTransition1.setCycleCount(2);
            scaleTransition1.setAutoReverse(true);
            ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(0.15), Cherrylogo);
            scaleTransition2.setFromX(1.0);
            scaleTransition2.setFromY(1.0);
            scaleTransition2.setToX(1.3);
            scaleTransition2.setToY(1.3);
            scaleTransition2.setCycleCount(2);
            scaleTransition2.setAutoReverse(true);
            ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(0.15), cherrycounter);
            scaleTransition3.setFromX(1.0);
            scaleTransition3.setFromY(1.0);
            scaleTransition3.setToX(1.3);
            scaleTransition3.setToY(1.3);
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
            setDefaultValues(stick1,ninja,p2,ShurikenImage);
        }
    }));
    private void moveObjectsBack2() {
        moveObjectsBack2.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack2.play();
    }



    private void stopobjectsmoving_1(){
        moveObjectsBack_1.stop();
    }
    Timeline moveObjectsBack_1 = new Timeline(new KeyFrame(Duration.seconds(0.002), event -> {
        if ((p2.getLayoutX() + p2.getWidth() > 100)) {
            for (javafx.scene.Node node : objectsToMove) {
                node.setLayoutX(node.getLayoutX() - 1);
            }
        }
        else{
            p1.setLayoutX(500);
            stopobjectsmoving_1();
            s.stop();
            bool11 = true;
            setDefaultValues(stick2,ninja,p1,ShurikenImage);
        }
    }));
    private void moveObjectsBack_1() {
        moveObjectsBack_1.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack_1.play();
    }

    private void stopobjectsmoving_2(){
        moveObjectsBack_2.stop();
    }
    Timeline moveObjectsBack_2 = new Timeline(new KeyFrame(Duration.seconds(0.002), event -> {
        if ((p1.getLayoutX() + p1.getWidth() > 100)) {
            for (javafx.scene.Node node : objectsToMove) {
                node.setLayoutX(node.getLayoutX() - 1);
            }
        }
        else{
            p2.setLayoutX(500);
            stopobjectsmoving_2();
            s.stop();
            bool11 = true;
            setDefaultValues(stick1,ninja,p2,ShurikenImage);
        }
    }));
    private void moveObjectsBack_2() {
        moveObjectsBack_2.setCycleCount(Animation.INDEFINITE);
        moveObjectsBack_2.play();
    }
}


