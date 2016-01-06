/**
 *
 * Sample solutions
 *
 * F(3) = 6
 *
 * F(5) = 120
 *
 * F(8) = 40320
 *
 * F(11) = 39916800
 */

package com.jvosantos.challenges.rupeal;

import java.util.Scanner;

public class Rupeal2 {

    private int count;

    public long fx(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("Negative Numbers aren't allowed!");
        }
        if (n == 0) {
            return 1;
        }

        long result = n--;

        for(; n > 1; n--) {
            result *= n;
            count++;
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            Scanner keyboard = new Scanner(System.in);

            System.out.print("Input a number: ");

            int n = keyboard.nextInt();

            Rupeal2 r = new Rupeal2();

            System.out.print("Value: " + r.fx(n) + " ");

            keyboard.close();

            System.out.println();

            System.out.println("Iterations: " + r.getCount());

            System.out.println("Please send the solution, sequence name and your cv to jobs@rupeal.com");
        }
        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
