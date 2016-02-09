package com.jvosantos.games.gameoflife.utils;

/**
 * Utility class to hold static functions that may be used in several different parts of the code.
 *
 * @author {@link "mailto:jvosantos@gmail.com" "Vasco Santos"}
 */
public class Utils {
    /**
     * Checks if a given bidimensional array is rectangular. More specifically, checks if all rows of a bidimensional have the same number of columns.
     * @param a the bidimensional array to be checked for rectangularity.
     * @return True if the array is rectangular, false otherwise.
     */
    public static boolean isRectangular(int[][] a) {
        for (int i = 0; i < a.length-1; i++) {
            if(a[i].length != a[i+1].length) {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds the maximum number of columns in a bidimensional array.
     * @param a The bidimensional array where the maximum number of columns will be calculated.
     * @return The maximum number of columns in the bidimensional array.
     */
    public static int getMaxColumns(int[][] a) {
        // No problem in defining 0 as the max because in the worst case the array will have 0 columns.
        int max = 0;

        for(int i = 0; i < a.length; i++) {
            if(a[i].length > max) {
                max = a[i].length;
            }
        }

        return max;
    }
}
