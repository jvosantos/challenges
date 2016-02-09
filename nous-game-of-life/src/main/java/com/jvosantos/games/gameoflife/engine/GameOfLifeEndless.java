package com.jvosantos.games.gameoflife.engine;

import com.jvosantos.games.gameoflife.utils.Utils;
import net.nous.test.GameOfLife;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A game of life implementation without any boundaries on how far the cells can grow.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class GameOfLifeEndless implements GameOfLife {

    /**
     * Constant from which under population death can occur.
     */
    private static final int UNDER_POPULATION = 2;
    /**
     * Constant from whcih over popoulation death can occur.
     */
    private static final int OVER_POPULATION = 3;
    /**
     * Constant with number of cells needed for reproduction to occur.
     */
    private static final int REPRODUCTION = 3;

    /**
     * A list of relative coordinates to determine the neighbours of a cell.
     */
    private static final List<Coordinate> neighbourCoordinatesMask;

    static {
        neighbourCoordinatesMask = new ArrayList<>();
        neighbourCoordinatesMask.add(new Coordinate(-1, -1));
        neighbourCoordinatesMask.add(new Coordinate(-1, 0));
        neighbourCoordinatesMask.add(new Coordinate(-1, 1));
        neighbourCoordinatesMask.add(new Coordinate(0, -1));
        neighbourCoordinatesMask.add(new Coordinate(0, 1));
        neighbourCoordinatesMask.add(new Coordinate(1, -1));
        neighbourCoordinatesMask.add(new Coordinate(1, 0));
        neighbourCoordinatesMask.add(new Coordinate(1, 1));
    }

    /**
     * A Map of cell states and coordinates, representing the cell world without any boundaries.
     */
    private Map<Coordinate, CellState> world;
    private PatternStrategy patternStrategy;

    private Size size;

    /**
     * Creates a new game of life without boundaries.
     */
    public GameOfLifeEndless() {
        clearWorld();
        patternStrategy = PatternStrategy.KEEP_INITIAL_PATTERN;
    }

    /**
     * Defines an output strategy for the next generations.
     *
     * @param patternStrategy The output strategy pattern
     */
    public void setOutputStrategy(PatternStrategy patternStrategy) {
        this.patternStrategy = patternStrategy;
    }

    /**
     * Defines a seed to be used for next generations. This overrides the current generation and resets the world.
     *
     * @param pattern The pattern to be used for next generations.
     */
    @Override public void seed(int[][] pattern) {
        // Empty the current world.
        clearWorld();

        // if output strategy is to keep initial pattern size, get dimensions of initial pattern
        if (patternStrategy == PatternStrategy.KEEP_INITIAL_PATTERN) {
            size = new Size(Utils.getMaxColumns(pattern), pattern.length);
        }

        // Add all living cells to the world.
        for (int i = 0; i < pattern.length; i++) {
            for (int j = 0; j < pattern[i].length; j++) {
                if (pattern[i][j] == CellState.ALIVE.ordinal()) {
                    // switch j and i on coordinate to map so that horizontal values are mapped onto
                    // x value of coordinate and vertical values are mapped onto y values of
                    // coordinate
                    world.put(new Coordinate(j, i), CellState.values()[pattern[i][j]]);
                }
            }
        }
    }

    @Override public int[][] next() {
        // advance the world to the next generation
        nextGeneration();

        return worldToArray();
    }

    /**
     * Converts the world into a bidimensional array.
     *
     * @return a bidimensional array containing the cells.
     */
    public int[][] worldToArray() {
        // create a new array with the width and height of the initial pattern
        int[][] worldArray = new int[size.getHeight()][size.getWidth()];

        // define a predicate to test if a given cell will go into the array or not
        Predicate<Coordinate> insideWorldArray =
            (coordinate) -> coordinate.getX() >= 0 && coordinate.getX() < size.getWidth()
                && coordinate.getY() >= 0 && coordinate.getY() < size.getHeight();

        // for each coordinate, test if the cell should go to the array or not and set to one if it should.
        world.keySet().stream().forEach(coordinate -> {
            if (insideWorldArray.test(coordinate)) {
                // for the same reason we switched i and j on seed, x and y must now be switched.
                worldArray[coordinate.getY()][coordinate.getX()] = world.get(coordinate).ordinal();
            }
        });

        return worldArray;
    }

    /**
     * Calculates the next generation of cells.
     */
    public void nextGeneration() {
        Map<Coordinate, CellState> nextGeneration = new HashMap<>();

        // find the dead neighbours of every cell
        Map<Coordinate, CellState> deadCells = new HashMap<>();

        // for every live cell, add the neighbours as dead cells only if a living cell in that
        // coordinate doesn't already exit
        world.keySet().stream().forEach(
            liveCellCoordinate -> neighbourCoordinatesMask.stream().forEach(maskCoordinate -> {
                Coordinate deadCellCoordinate = liveCellCoordinate.add(maskCoordinate);

                if (!world.containsKey(deadCellCoordinate)) {
                    deadCells.put(deadCellCoordinate, CellState.DEAD);
                }
            }));

        // For every live cell, find out the cell fate on the next generation.
        // If Death (a.k.a. JVM) decides to reap the cell's life, don't add the cell to the next
        // generation world.
        world.keySet().stream().forEach(liveCellCoordinate -> {
            // count the live neighbours
            int neighbours = neighbourCoordinatesMask.stream()
                .map(maskCoordinate -> maskCoordinate.add(liveCellCoordinate)).mapToInt(
                    neighbourCoordinate -> world.getOrDefault(neighbourCoordinate, CellState.DEAD)
                        .ordinal()).sum();

            // Add current cell only if it will stay alive in the next generation
            if (neighbours >= UNDER_POPULATION && neighbours <= OVER_POPULATION) {
                nextGeneration.put(liveCellCoordinate, CellState.ALIVE);
            }
        });

        // For every dead cell, find out if there are enough neighbours to reproduce. If enough
        // neighbours are around, add it to the next generation world.
        deadCells.keySet().stream().forEach(deadCellCoordinate -> {
            // count the live neighbours
            int neighbours = neighbourCoordinatesMask.stream()
                .map(maskCoordinate -> maskCoordinate.add(deadCellCoordinate)).mapToInt(
                    neighbourCoordinate -> world.getOrDefault(neighbourCoordinate, CellState.DEAD)
                        .ordinal()).sum();

            if (neighbours == REPRODUCTION) {
                nextGeneration.put(deadCellCoordinate, CellState.ALIVE);
            }
        });

        // advance the world to the next generation
        world = nextGeneration;
    }

    /**
     * Clear the current world, killing all cells.
     */
    public void clearWorld() {
        world = new HashMap<>();
    }
}
