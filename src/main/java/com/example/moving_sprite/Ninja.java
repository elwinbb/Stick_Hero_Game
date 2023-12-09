package com.example.moving_sprite;

import javafx.scene.image.Image;

import java.io.File;

public class Ninja {
    private double Spritenumber = 1;

    public void setSpritenumber(double spritenumber) {
        Spritenumber = spritenumber;
    }

    public double getSpritenumber() {
        return Spritenumber;
    }

    double x = 0;
    double y = 0;
    double speed = 1;
    double angle = 0;
    boolean landed;
    boolean alive = true;
    public File getFile(String fileName){
        return new File(getClass().getResource(fileName).getPath());
    }
    Image sp1 = new Image(getFile("Sprites/Stick_Hero_Ninja-1.png").getAbsolutePath());
    Image sp2 = new Image(getFile("Sprites/Stick_Hero_Ninja-2.png").getAbsolutePath());
    Image sp3 = new Image(getFile("Sprites/Stick_Hero_Ninja-3.png").getAbsolutePath());
    Image sp4 = new Image(getFile("Sprites/Stick_Hero_Ninja-4.png").getAbsolutePath());
    Image sp_1 = new Image(getFile("SpritesReversed/Stick_Hero_Ninja-1.png").getAbsolutePath());
    Image sp_2 = new Image(getFile("SpritesReversed/Stick_Hero_Ninja-2.png").getAbsolutePath());
    Image sp_3 = new Image(getFile("SpritesReversed/Stick_Hero_Ninja-3.png").getAbsolutePath());
    Image sp_4 = new Image(getFile("SpritesReversed/Stick_Hero_Ninja-4.png").getAbsolutePath());
    Image ded = new Image(getFile("Sprites/Stick_Hero_Ninja-Dead.png").getAbsolutePath());
}
