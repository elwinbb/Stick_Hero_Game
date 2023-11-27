package com.example.moving_sprite;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Block {
    double width;
    final double layouty = bonusy = 400;
    final double height = 200;
    double layoutx;
    double end;
    double bonusx;
    double bonusy;
    Random random = new Random();
    public Block(){ //setting manual values for now, will make it random later
        this.width = 50; //= range is from 25 to 150
        this.layoutx = 400; // = range is from
        this.end = layoutx + width;
        this.bonusx = layoutx + width/2 - 6;
    }
    public void setDimensions(Rectangle block,Rectangle bonus){
        block.setLayoutX(this.layoutx);
        block.setLayoutY(this.layouty);
        block.setWidth(this.width);
        block.setHeight(this.height);
        bonus.setLayoutX(this.bonusx);
        bonus.setLayoutY(400);
        bonus.setWidth(12);
        bonus.setHeight(8);
    }
    //FOR FUTURE ME, THE SHURIKEN AND CHERRY SHOULD ONLY SPAWN IF THE DISTANCE IS WITHIN A CERTAIN RANGE, BECAUSE IF IT IS TOO LESS HE NINJA
    // WILL DIE WHATSOEVER SO FIND THE MINIMUM DISTANCE THAT YOU DONT WANT THE SHURIKEN TO SPAWN IN ,
}