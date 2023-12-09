package com.example.moving_sprite;

import java.io.*;

public class Score implements Serializable {
    private int highscore;
    private int cherrycount;

    private int score;
    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public void setCherrycount(int cherrycount) {
        this.cherrycount = cherrycount;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Score() {
        this.highscore = 0;
        this.cherrycount = 20;
        score=0;
    }

    public int getCherrycount() {
        return cherrycount;
    }
    public void savescore() throws IOException {
        ObjectOutputStream out=null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/moving_sprite/score.txt"));
            out.writeObject(this);
        }
        finally {
            out.close();
        }
    }
    public static Score loadscore() throws IOException,ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("src/main/resources/com/example/moving_sprite/score.txt"));
            Score score = (Score) in.readObject();
            return score;
        }
        catch (FileNotFoundException e){
            Score score=new Score();
            return score;
        }
        finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static Score makescore() throws IOException, ClassNotFoundException {
        Score score=loadscore();
        if(score==null){
            score=new Score();
            return score;
        }
        else{
            return score;
        }
    }

}
