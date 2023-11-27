package com.example.moving_sprite;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineSwitchingExample extends Application {

    private static final Duration TIMELINE_INTERVAL = Duration.seconds(1);

    private Timeline timeline1;
    private Timeline timeline2;
    private Thread thread1;
    private Thread thread2;

    @Override
    public void start(Stage primaryStage) {
        timeline1 = createTimeline("Timeline1");
        timeline2 = createTimeline("Timeline2");

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);

        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        primaryStage.setTitle("Timeline Switching Example");
        primaryStage.setScene(scene);
        primaryStage.show();

        startTimelines();
    }

    private Timeline createTimeline(String message) {
        return new Timeline(
                new KeyFrame(TIMELINE_INTERVAL, e -> System.out.println(message))
        );
    }

    private void startTimelines() {
        // Start thread1 for timeline1
        thread1 = new Thread(() -> {
            while (true) {
                timeline1.play();
                try {
                    Thread.sleep((long) TIMELINE_INTERVAL.toMillis());
                } catch (InterruptedException ignored) {
                }
                timeline1.stop();
            }
        });
        thread1.start();

        // Start thread2 for timeline2
        thread2 = new Thread(() -> {
            while (true) {
                timeline2.play();
                try {
                    Thread.sleep((long) TIMELINE_INTERVAL.toMillis());
                } catch (InterruptedException ignored) {
                }
                timeline2.stop();
            }
        });
        thread2.start();
    }

    private void handleKeyPress(KeyCode keyCode) {
        if (keyCode == KeyCode.SPACE) {
            // Switch between threads when the space bar is pressed
            if (timeline1.getStatus() == Timeline.Status.RUNNING) {
                timeline1.stop();
                timeline2.play();
            } else {
                timeline2.stop();
                timeline1.play();
            }
        }
    }

    @Override
    public void stop() {
        // Stop the threads when the application is closed
        thread1.interrupt();
        thread2.interrupt();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
