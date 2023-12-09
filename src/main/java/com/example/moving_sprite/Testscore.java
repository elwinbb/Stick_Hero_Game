package com.example.moving_sprite;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class Testscore {
    @Test
    public void testscoreload() throws IOException, ClassNotFoundException {

        Score score=Score.loadscore();
        Assert.assertTrue(score instanceof Score);
        Assert.assertEquals(0,score.getScore());

    }
}
