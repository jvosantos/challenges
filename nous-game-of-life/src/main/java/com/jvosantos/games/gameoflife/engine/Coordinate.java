package com.jvosantos.games.gameoflife.engine;

/**
 * A coordinate with an integer x and an integer y.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class Coordinate {
    private final int x;
    private final int y;

    /**
     * Creates a new origin coordinate
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Create a new coordinate with the given x and y.
     * @param x An integer representing an horizontal distance from the origin.
     * @param y An integer representing a vertical distance from the origin.
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the current horizontal distance
     * @return The x value.
     */
    public int getX() {
        return x;
    }

    /**
     * Get the current vertical distance
     * @return The y value.
     */
    public int getY() {
        return y;
    }

    /**
     * Get a new coordinate based on the current coordinate and the addition of another coordinate.
     * Specifically, this method sums current x value with the x value of the parameter coordinate and the current y value with the y value of the parameter coordinate.
     * @param coordinate The coordinate to be added.
     * @return A new coordinate resulting from the addition.
     */
    public Coordinate add(Coordinate coordinate) {
        return new Coordinate(x + coordinate.x, y + coordinate.y);
    }

    /**
     * Checks if two coordinates are equal based on x value and y value.
     * @param o the object to check for equality
     * @return True if this coordinate is equal to the provided one, false otherwise.
     */
    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Coordinate that = (Coordinate) o;

        if (y != that.y)
            return false;
        return x == that.x;

    }

    /**
     * Returns the hashcode of the current coordinate based on the x and y values.
     * @return an integer representing the calculated hashcode.
     */
    @Override public int hashCode() {
        int result = y;
        result = 31 * result + x;
        return result;
    }

    /**
     * Returns a string representing this coordinate in the form of (x, y)
     * @return a string representing the current coordinate.
     */
    @Override public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
