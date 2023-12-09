package com.example.moving_sprite;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Block {
    private static Block block =null;
    private double width;
    private final double layouty = bonusy = 400;
    private final double height = 200;
    private double layoutx;
    private double end;
    private double bonusx;
    private double bonusy;


    public static Block getinstance(){
        if(block==null){
            block=new Block();
        }
        return block;
    }
    public void setDimensions(Rectangle block, Rectangle bonus, ImageView s) {
        Random random = new Random();
        Random random1 = new Random();
        this.width = (random.nextInt(6) + 2) * 12.5;
        if (this.width > 100) {
            this.layoutx = (random1.nextInt(7) + 8) * 25;
        } else {
            this.layoutx = (random1.nextInt(10) + 8) * 25;
        }
        this.end = layoutx + width;
        this.bonusx = layoutx + width / 2 - 6;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.1), block);
        translateTransition.setFromX(500 + this.layoutx);
        translateTransition.setToX(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.1), bonus);
        translateTransition2.setFromX(500 + this.layoutx);
        translateTransition2.setToX(0);
        translateTransition2.setCycleCount(1);
        translateTransition2.setAutoReverse(false);
        TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(0.1), s);
        translateTransition3.setFromX(500 + this.layoutx);
        translateTransition3.setToX(0);
        translateTransition3.setCycleCount(1);
        translateTransition3.setAutoReverse(false);
        block.setLayoutX(this.layoutx);
        block.setLayoutY(this.layouty);
        block.setWidth(this.width);
        block.setHeight(this.height);
        bonus.setLayoutX(this.bonusx);
        bonus.setLayoutY(400);
        bonus.setWidth(12);
        bonus.setHeight(8);
    }
}