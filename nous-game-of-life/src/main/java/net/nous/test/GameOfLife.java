package net.nous.test;

/*
 * A game of life. A zero player game determined by the initial
 * state of the integer array
 */
public interface GameOfLife {
    /*
     * set the initial state
     */
    void seed(int[][] pattern);

    /*
     * Output the next state in the sequence
     */
    int[][] next();
}
