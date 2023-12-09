package com.example.moving_sprite;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Score score=Score.loadscore();
    @FXML
    public Text scoretext=new Text();
    @FXML
    public Text highscoretext=new Text();

    public SceneController() throws IOException, ClassNotFoundException {
    }

    public void initialize(){
        scoretext.setText(String.valueOf(score.getScore()));
        highscoretext.setText(String.valueOf(score.getHighscore()));

    }

    public void switchtogame(MouseEvent e) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("PlayingScreen.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene.getRoot().requestFocus();
            stage.setTitle("StickHeroGame");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the exception for debugging purposes
        }
    }
    public void switchtohome(MouseEvent e) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("home_screen.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the exception for debugging purposes
        }
    }
    public void restartgame(MouseEvent e) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("PlayingScreen.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene.getRoot().requestFocus();
            stage.setTitle("StickHeroGame");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the exception for debugging purposes
        }
    }
    public void switchtogameover(ImageView image) throws IOException {
        fxmlLoader = new FXMLLoader(getClass().getResource("game_over.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage = (Stage) image.getScene().getWindow();
        Audio swoosh1=Audio.getaudio("SWOOSH.wav");
        swoosh1.stop();
        Audio swoosh=Audio.getaudio("SWOOSH.wav");
        swoosh.playaudio();
        stage.setScene(scene);
        stage.show();
    }

    public void blurelement(Node element){
        BoxBlur blur = new BoxBlur();
        blur.setWidth(5);
        blur.setHeight(5);
        blur.setIterations(3);
        element.setEffect(blur);
    }
    public void blurscreen(AnchorPane anchorPane){
        for (javafx.scene.Node node : anchorPane.getChildren()){
            if (node instanceof Pane) {
                continue;
            } else {
                blurelement(node);
            }
        }
    }
    public void unblurscreen(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof Pane) {
                continue;
            } else {
                unblurelement(node);
            }
        }
    }

    public void unblurelement(Node element) {
        element.setEffect(null); // Clear the effect to remove the blur
    }

    public void gameoverscreen(int score,int highscore){
        System.out.println(score+highscore);
        highscoretext.setText("123");
        scoretext.setText(String.valueOf(score));

    }

}
