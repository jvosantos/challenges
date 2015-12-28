package com.jvosantos.challenges.nous;

/**
 * Problem Description:
 *
 * Write a program in Java which takes an array of integers which represents the price of a stock
 * on each day over a certain period of time and returns an object with the buy price and the sell
 * price which gives the maximum revenue achievable. You must buy before selling. You may only
 * buy and sell once.
 */
public class StockPriceChallenge {
    public StockPurchase solution(int[] A) {
        if(A == null || A.length < 1) {
            return new StockPurchase();
        }

        int buyingDay = 0;
        int sellingDay = 0;

        int max = 0;
        int result = 0;

        for(int i = A.length - 1; i >= 0; i--) {
            if(A[i] > max) {
                max = A[i];
                sellingDay = i;
            }

            int tmpResult = max - A[i];

            if(tmpResult > result) {
                result = tmpResult;
                buyingDay = i;
            }
        }

        return new StockPurchase(A[buyingDay], A[sellingDay]);
    }
}
