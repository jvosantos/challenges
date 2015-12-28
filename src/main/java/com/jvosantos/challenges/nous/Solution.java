package com.jvosantos.challenges.nous;

public class Solution {
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
