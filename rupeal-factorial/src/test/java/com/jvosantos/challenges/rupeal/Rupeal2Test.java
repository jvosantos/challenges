package com.jvosantos.challenges.rupeal;

import org.junit.Assert;
import org.junit.Test;
import java.util.Random;

public class Rupeal2Test {

    @Test(expected=IllegalArgumentException.class)
    public void testFactorialAnyNegativeNumber() {
        new Rupeal2().fx(-1);
    }

    @Test
    public void testFactorialZero() {
        Assert.assertEquals(1, new Rupeal2().fx(0));
    }

    @Test
    public void testFactorialOne() {
        Assert.assertEquals(1, new Rupeal2().fx(1));
    }

    @Test
    public void testFactorialThree() {
        Assert.assertEquals(6, new Rupeal2().fx(3));
    }

    @Test
    public void testFactorialFive() {
        Assert.assertEquals(120, new Rupeal2().fx(5));
    }

    @Test
    public void testFactorialEight() {
        Assert.assertEquals(40320, new Rupeal2().fx(8));
    }

    @Test
    public void testFactorialEleven() {
        Assert.assertEquals(39916800, new Rupeal2().fx(11));
    }
}
