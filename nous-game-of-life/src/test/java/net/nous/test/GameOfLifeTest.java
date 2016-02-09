package net.nous.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link net.nous.test.GameOfLife}
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public abstract class GameOfLifeTest {

    /**
     * Returns an implementation of {@link net.nous.test.GameOfLife}. To be implemented by test classes of the interface implementations.
     *
     * @return An implementation of {@link net.nous.test.GameOfLife}.
     */
    protected abstract GameOfLife createGame();

    /**
     * Tests GameOfLife with an oscillator placed on the edge of a board. The pattern sequence is the following:
     * <p>
     * 1
     * 111    010    111
     * 000 => 010 => 000
     * 000    000    000
     * <p>
     * Notice there is a cell outside of the board.
     */
    @Test public void testBorderBlinker() {
        int[][] borderHorizontalBlinker = {{1, 1, 1}, {0, 0, 0}, {0, 0, 0}};
        int[][] borderVerticalBlinker = {{0, 1, 0}, {0, 1, 0}, {0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(borderHorizontalBlinker);

        Assert.assertArrayEquals(borderVerticalBlinker, gameOfLife.next());
        Assert.assertArrayEquals(borderHorizontalBlinker, gameOfLife.next());
        Assert.assertArrayEquals(borderVerticalBlinker, gameOfLife.next());
        Assert.assertArrayEquals(borderHorizontalBlinker, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with an oscillator. The generations will have the following period:
     * <p>
     * 000    010    000
     * 111 => 010 => 111
     * 000    010    000
     */
    @Test public void testBlinker() throws Exception {
        int[][] blinkerVertical = {{0, 1, 0}, {0, 1, 0}, {0, 1, 0}};
        int[][] blinkerHorizontal = {{0, 0, 0}, {1, 1, 1}, {0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(blinkerVertical);

        Assert.assertArrayEquals(blinkerHorizontal, gameOfLife.next());

        Assert.assertArrayEquals(blinkerVertical, gameOfLife.next());

        Assert.assertArrayEquals(blinkerHorizontal, gameOfLife.next());

        Assert.assertArrayEquals(blinkerVertical, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with several rotations of a preblock. An example of a preblock is the following:
     * <p>
     * 0000    0000    0000
     * 0010 => 0110 => 0110
     * 0110    0110    0110
     * 0000    0000    0000
     */
    @Test public void testPreBlock() {
        int[][] preBlockTopLeft = {{0, 0, 0, 0}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        int[][] preBlockTopRight = {{0, 0, 0, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        int[][] preBlockBottomLeft = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}};
        int[][] preBlockBottomRight = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 0, 0, 0}};
        int[][] block = {{0, 0, 0, 0}, {0, 1, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(preBlockTopLeft);
        Assert.assertArrayEquals(block, gameOfLife.next());
        Assert.assertArrayEquals(block, gameOfLife.next());

        gameOfLife.seed(preBlockTopRight);
        Assert.assertArrayEquals(block, gameOfLife.next());
        Assert.assertArrayEquals(block, gameOfLife.next());

        gameOfLife.seed(preBlockBottomLeft);
        Assert.assertArrayEquals(block, gameOfLife.next());
        Assert.assertArrayEquals(block, gameOfLife.next());

        gameOfLife.seed(preBlockBottomRight);
        Assert.assertArrayEquals(block, gameOfLife.next());
        Assert.assertArrayEquals(block, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with a still life pattern (boat), i.e., a pattern that won't change. The boat pattern is the following:
     * <p>
     * 00000    00000
     * 01100 => 01100
     * 01010    01010
     * 00100    00100
     * 00000    00000
     */
    @Test public void testStillLife() throws Exception {
        int[][] boat =
            {{0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {0, 1, 0, 1, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(boat);
        Assert.assertArrayEquals(boat, gameOfLife.next());
        Assert.assertArrayEquals(boat, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with a pattern that is doomed to die after a few generations. The sequence is the following:
     * <p>
     * 10000    00000    00000    00000
     * 01000    01000    00000    00000
     * 00100 => 00100 => 00100 => 00000
     * 00010    00010    00000    00000
     * 00001    00000    00000    00000
     */
    @Test public void testDoomedLife() throws Exception {
        int[][] fiveCells =
            {{1, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 1}};
        int[][] threeCells =
            {{0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}};
        int[][] oneCell =
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        int[][] death =
            {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(fiveCells);
        Assert.assertArrayEquals(threeCells, gameOfLife.next());
        Assert.assertArrayEquals(oneCell, gameOfLife.next());
        Assert.assertArrayEquals(death, gameOfLife.next());
        Assert.assertArrayEquals(death, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with an oscillator provided by the nous exercise description: The pattern and its period is the following:
     * <p>
     * 0000000000    0000000000    0000000000
     * 0000000000    0000000000    0000000000
     * 0000000000    0000000000    0000000000
     * 0000000000    0000010000    0000000000
     * 0000111000    0000100100    0000111000
     * 0000011100 => 0000100100 => 0000011100
     * 0000000000    0000001000    0000000000
     * 0000000000    0000000000    0000000000
     * 0000000000    0000000000    0000000000
     * 0000000000    0000000000    0000000000
     */
    @Test public void testNousOscillator() {
        int[][] nousOscillator1 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        int[][] nousOscillator2 = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 1, 0, 0}, {0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(nousOscillator1);

        Assert.assertArrayEquals(nousOscillator2, gameOfLife.next());
        Assert.assertArrayEquals(nousOscillator1, gameOfLife.next());
    }

    /**
     * Tests GameOfLife with a row of 15 blinkers, i.e., several oscillator patterns. The pattern and its period is the following:
     * <p>
     * 00000000000000000000000000000000000000000000000000000000000
     * 11101110111011101110111011101110111011101110111011101110111
     * 00000000000000000000000000000000000000000000000000000000000
     *                            |
     *                            v
     * 01000100010001000100010001000100010001000100010001000100010
     * 01000100010001000100010001000100010001000100010001000100010
     * 01000100010001000100010001000100010001000100010001000100010
     */
    @Test public void testRowOfBlinkers() {
        int[][] rowOfHorizontalBlinkers =
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0},
                {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0,
                    1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1,
                    0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0}};

        int[][] rowOfVerticalBlinkers =
            {
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(rowOfHorizontalBlinkers);

        Assert.assertArrayEquals(rowOfVerticalBlinkers, gameOfLife.next());
        Assert.assertArrayEquals(rowOfHorizontalBlinkers, gameOfLife.next());
        Assert.assertArrayEquals(rowOfVerticalBlinkers, gameOfLife.next());
        Assert.assertArrayEquals(rowOfHorizontalBlinkers, gameOfLife.next());
    }

}
