package com.jvosantos.games.gameoflife.engine;

/**
 * Holds size of a two dimensional window, i.e., something with a width and a height.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class Size {
    private int width;
    private int height;

    /**
     * Creates new a Size with 0 width and 0 height.
     */
    public Size() {
        this(0, 0);
    }

    /**
     * Creates a new Size with given width and given height.
     * @param width the width value
     * @param height the height value
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width.
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height.
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
