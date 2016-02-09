package com.jvosantos.games.gameoflife.engine;

import net.nous.test.GameOfLife;
import com.jvosantos.games.gameoflife.utils.Utils;

/**
 * A game of life implementation with boundaries to how far a cell can grow the size of the inital pattern.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class GameOfLifeConstrained implements GameOfLife {

    private static final int DEAD = 0;
    private static final int ALIVE = 1;

    private int[][] pattern;
    private int rows;
    private int cols;

    /**
     * Creates a new constrained game of life
     */
    public GameOfLifeConstrained() {
    }

    /***
     * Sets the inital pattern of the game. The pattern is a bidimensional integer array with each
     * integer representing the cell state, 0 means the cell is dead, 1 means the cell is alive.
     * The pattern must be a rectangular array and the array must be at least a 1x1 matrix.
     *
     * @param pattern A bidimensional array of integers representing the state of the cells.
     *                1 indicates the cell is alive, 0 indicates the cell is dead.
     */
    @Override
    public void seed(int[][] pattern) {
        if(!Utils.isRectangular(pattern)) {
            throw new IllegalArgumentException("Pattern must be a rectangular array.");
        }

        if(pattern.length <= 0) {
            throw new IllegalArgumentException("Pattern must have at least one row.");
        }

        if(pattern[0].length <= 0) {
            throw new IllegalArgumentException("Pattern must have at least one column.");
        }

        this.pattern = pattern;
        rows = pattern.length;
        cols = pattern[0].length;

//        System.out.println("initial generation:");
//        System.out.println(this);
    }

    /**
     * Calculates the new generation of cells according to conway's game of life rules.
     *
     * @return A bidimensional integer array representing the new generation of cells.
     */
    @Override
    public int[][] next() {
        int[][] nextGeneration = new int[rows][cols];

        for(int i = 0; i < pattern.length; i++) {
            for(int j = 0; j < pattern[i].length; j++) {
                nextGeneration[i][j] = decideCellState(i, j);
            }
        }

//        System.out.println("new Generation: ");
        pattern = nextGeneration;
//        System.out.println(this);

        return pattern;
    }

    /**
     * Calculates the state in which a cell will be in the next generation.
     *
     * @param row The row in which a cell currently is.
     * @param col The column in which a cell currently is.
     * @return
     */
    private int decideCellState(int row, int col) {
        int neighbourCount = 0;
        int nextState;

        for(int i = -1; i <= 1; i++) {
            int currentRow = row + i;

            if(currentRow < 0 || currentRow >= rows) {
                continue;
            }

            for(int j = -1; j <= 1; j++) {
                int currentCol = col + j;

                if(currentCol < 0 || currentCol >= cols || (j == 0 && i == 0)) {
                    continue;
                }

                neighbourCount += pattern[currentRow][currentCol];
            }
        }

        switch(neighbourCount) {
            case 0:
            case 1:
                nextState = DEAD;
                break;
            case 2:
                if(pattern[row][col] == DEAD) {
                    nextState = DEAD;
                    break;
                }
            case 3:
                if(pattern[row][col] == DEAD) {
                    nextState = ALIVE;
                } else {
                    nextState = ALIVE;
                }
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                nextState = DEAD;
                break;
            default:
                throw new IllegalStateException("More than 8 neighbours???");
        }

        return nextState;
    }
}
