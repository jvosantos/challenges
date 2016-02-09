package com.jvosantos.games.gameoflife.engine;

import net.nous.test.GameOfLife;
import net.nous.test.GameOfLifeTest;

/**
 * Tests for {@link com.jvosantos.games.gameoflife.engine.GameOfLifeEndless}
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class GameOfLifeEndlessTest extends GameOfLifeTest {
    /**
     * Provides an instance of {@link com.jvosantos.games.gameoflife.engine.GameOfLifeEndless} to be used in {@link net.nous.test.GameOfLifeTest} tests.
     * @return An instantiated {@link com.jvosantos.games.gameoflife.engine.GameOfLifeEndless}.
     */
    @Override protected GameOfLife createGame() {
        return new GameOfLifeEndless();
    }
}
