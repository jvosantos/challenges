package com.jvosantos.games.gameoflife.ui;

import com.jvosantos.games.gameoflife.engine.GameMode;
import com.jvosantos.games.gameoflife.engine.LifePatterns;

import java.util.Arrays;

/**
 * Console wrapper. Responsible for printing the user interface on the console.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class ConsoleInterface {

    private char deadCharacter;
    private char aliveCharacter;

    /**
     * Create a new console interface with '#' for live cells and ' ' for dead cells.
     */
    public ConsoleInterface() {
        this('#', ' ');
    }

    /**
     * Create a new console interface with the given characters for live and dead cells.
     * @param aliveCharacter
     * @param deadCharacter
     */
    public ConsoleInterface(char aliveCharacter, char deadCharacter) {
        this.aliveCharacter = aliveCharacter;
        this.deadCharacter = deadCharacter;
    }

    /**
     * Prints the given board to the console.
     * @param board an integer bidimensional array where 0 represents the dead cells and 1 the live cells.
     */
    public void printBoard(int[][] board) {
        System.out.println("============  Game of life  ============");
        Arrays.stream(board).forEach(ints -> {
            Arrays.stream(ints)
                .forEach(i -> System.out.print(i == 0 ? deadCharacter : aliveCharacter));
            System.out.println();
        });

    }

    /**
     * Prints a short information on what settings are available for the user to define.
     */
    public static void printHelp() {
        System.out.println("Usage: java -jar game-of-life.jar [OPTION]...");
        System.out.println("A java implementation of John Conway's game of life.");
        System.out.println();
        System.out.println(
            "  --max-generations <NUMBER>         \tMaximum number of generations to go through before program terminates.");
        System.out.println(
            "  --ms-between-generations <NUMBER>  \tNumber of milliseconds to wait between each generation.");
        System.out.println("  --endless                 \tKeep going between generations forever.");
        System.out.printf(
            "  --pattern <PATTERN>                \tUse a predefined pattern. Possible patterns: %s.%n",
            Arrays.toString(LifePatterns.values()));

        System.out.printf(
            "  --mode <MODE>                      \tDefines the implementation to be used. Possible implementations: %s, %s.%n",
            GameMode.CONSTRAINED.toString(), GameMode.ENDLESS.toString());
        System.out.printf(
            "                                     \t\t %s - Cells live in a constrained world, meaning they cannot grow outside of the defined world.%n",
            GameMode.CONSTRAINED.toString());
        System.out.printf(
            "                                     \t\t %s - Cells live in a endless world, meaning they can grow in all directions without any limit.%n",
            GameMode.ENDLESS.toString());
        System.out.println(
            "  --alive-character <CHARACTER>      \tCharacter to be used when printing live cells.");
        System.out.println(
            "  --dead-character <CHARACTER>       \tCharacter to be used when printing dead cells.");
        System.out.println(
            "  --input-pattern <PATTERN_TYPE>     \tType of pattern used in pattern file to define initial seed. Currently, the only pattern available is BINARY, meaning 0 represents dead cells and 1 live cells.");
        System.out.println(
            "  --pattern-file <FILE>              \tFile with initial feed. File is expected to contain a a rectangular matrix with 0 for dead cells and 1 for live cells. No space between cells and each line contains a row of the matrix.");
    }

}
