package com.example.moving_sprite;

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

import java.awt.*;
import java.io.IOException;

public class SceneController {
    private Stage stage;
    private FXMLLoader fxmlLoader;
    private Scene scene;
    public void switchtogame(MouseEvent e) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("PlayingScreen.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the exception for debugging purposes
        }
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

}
