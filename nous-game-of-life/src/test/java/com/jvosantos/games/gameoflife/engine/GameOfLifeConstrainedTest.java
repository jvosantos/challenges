package com.jvosantos.games.gameoflife.engine;

import net.nous.test.GameOfLife;
import net.nous.test.GameOfLifeTest;
import org.junit.Assert;

/**
 * Tests for {@link com.jvosantos.games.gameoflife.engine.GameOfLifeConstrained}
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class GameOfLifeConstrainedTest extends GameOfLifeTest {

    /**
     * Provides an instance of {@link com.jvosantos.games.gameoflife.engine.GameOfLifeConstrained} to be used in {@link net.nous.test.GameOfLifeTest} tests.
     * @return An instantiated {@link com.jvosantos.games.gameoflife.engine.GameOfLifeConstrained}.
     */
    public GameOfLife createGame() {
        return new GameOfLifeConstrained();
    }

    /**
     * Overrides test case {@link GameOfLifeTest#testBorderBlinker()} due to this implementation actually behaving differently in a constrained mode.
     * Due to the fact that in constrained mode, the cells cannot grow outside the defined initial seed, a border blinker will fail to oscillate.
     */
    @Override
    public void testBorderBlinker() {
        int[][] borderHorizontalBlinker = {{1, 1, 1}, {0, 0, 0}, {0, 0, 0}};
        int[][] borderVerticalBlinker = {{0, 1, 0}, {0, 1, 0}, {0, 0, 0}};
        int[][] death = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        GameOfLife gameOfLife = createGame();

        gameOfLife.seed(borderHorizontalBlinker);

        Assert.assertArrayEquals(borderVerticalBlinker, gameOfLife.next());
        Assert.assertArrayEquals(death, gameOfLife.next());
    }
}
