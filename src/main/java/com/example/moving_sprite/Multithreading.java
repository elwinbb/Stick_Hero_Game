package com.example.moving_sprite;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;

import java.util.ResourceBundle;

// ... (other imports)

public class Multithreading implements Initializable {
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Start the first action in a new thread
        Thread growThread = new Thread(() -> {
            // Execute the first action (e.g., grow)
            // Your action logic here (e.g., start a timeline for grow)

            // Update the UI when the first action is complete
            Platform.runLater(() -> {
                // Update UI or trigger the next action
            });
        });
        growThread.start();

        // Start the second action in a new thread
        Thread moveThread = new Thread(() -> {
            // Wait for the first action to complete before executing the second one
            try {
                growThread.join(); // Wait for the growThread to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Execute the second action (e.g., move)
            // Your action logic here (e.g., start a timeline for move)

            // Update the UI when the second action is complete
            Platform.runLater(() -> {
                // Update UI or trigger the next action
            });
        });
        moveThread.start();

        // Start the third action in a new thread
        Thread fallThread = new Thread(() -> {
            // Wait for the second action to complete before executing the third one
            try {
                moveThread.join(); // Wait for the moveThread to complete
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Execute the third action (e.g., fall)
            // Your action logic here (e.g., start a timeline for fall)

            // Update the UI when the third action is complete
            Platform.runLater(() -> {
                // Update UI or trigger the next action
            });
        });
        fallThread.start();
    }
}

