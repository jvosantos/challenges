package com.jvosantos.games.gameoflife.engine;

import net.nous.test.GameOfLife;

/**
 * Factory to obtain game of life implementations.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class GameEngineFactory {
    /**
     * Returns a new instance of game of life based on the give {@link com.jvosantos.games.gameoflife.engine.GameMode}
     * @param mode the game mode deciding what implementation to return.
     * @return A new {@link net.nous.test.GameOfLife} implementation.
     */
    public static GameOfLife getEngine(GameMode mode) {
        GameOfLife gameOfLife = null;

        switch(mode) {
            case CONSTRAINED:
                gameOfLife = new GameOfLifeConstrained();
                break;
            case ENDLESS:
                gameOfLife = new GameOfLifeEndless();
                break;
        }

        return gameOfLife;
    }
}
