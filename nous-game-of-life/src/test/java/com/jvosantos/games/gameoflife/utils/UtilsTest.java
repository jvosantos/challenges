package com.jvosantos.games.gameoflife.utils;


import org.junit.Assert;
import org.junit.Test;

/**
 * Holds the test cases for {@link com.jvosantos.games.gameoflife.utils.Utils}.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class UtilsTest {

    /**
     * Tests utility function {@link com.jvosantos.games.gameoflife.utils.Utils#isRectangular(int[][])} with three arrays: a square, a rectangular and a ragged.
     */
    @Test public void testIsRectangular() {
        // square array of 3x3
        Assert
            .assertEquals(true, Utils.isRectangular(new int[][] {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}));

        // rectangular array 2x3
        Assert.assertEquals(true, Utils.isRectangular(new int[][] {{0, 0, 0}, {0, 0, 0}}));

        // ragged array 3-2-3
        Assert.assertEquals(false, Utils.isRectangular(new int[][] {{0, 0, 0}, {0, 0}, {0, 0, 0}}));
    }

    /**
     * Tests utility funciton {@link com.jvosantos.games.gameoflife.utils.Utils#getMaxColumns(int[][])} with two arrays, a rectangular and a ragged.
     */
    @Test public void testGetMaxColumns() {
        // rectangular array 3x3
        Assert.assertEquals(3, Utils.getMaxColumns(new int[][] {{0, 0, 0}, {0, 0, 0}}));

        // ragged array 3-2-1
        Assert.assertEquals(3, Utils.getMaxColumns(new int[][] {{0, 0, 0}, {0, 0}, {0}}));

        // ragged array 1-2-3
        Assert.assertEquals(3, Utils.getMaxColumns(new int[][] {{0}, {0, 0}, {0, 0, 0}}));

        // ragged array 2-3-1
        Assert.assertEquals(3, Utils.getMaxColumns(new int[][] {{0, 0}, {0, 0, 0}, {0}}));
    }

}
