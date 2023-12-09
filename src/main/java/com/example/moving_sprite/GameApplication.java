package com.example.moving_sprite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("home_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getRoot().requestFocus();
        Audio bg = Audio.getaudio("bgmusic.wav");
        bg.playaudio();
        Audio swoosh=Audio.getaudio("SWOOSH.wav");
        swoosh.playaudio();
        stage.setTitle("StickHeroGame");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}