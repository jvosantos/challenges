package com.jvosantos.challenges.nous;

public class StockPurchase {
    private int buyingPrice;
    private int sellingPrice;

    public StockPurchase() {
        buyingPrice = sellingPrice = 0;
    }

    public StockPurchase(int buyingPrice, int sellingPrice) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getRevenue() {
        return sellingPrice - buyingPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StockPurchase that = (StockPurchase) o;

        if (buyingPrice != that.buyingPrice)
            return false;
        return sellingPrice == that.sellingPrice;
    }

    @Override
    public int hashCode() {
        int result = buyingPrice;
        result = 31 * result + sellingPrice;
        return result;
    }

    @Override
    public String toString() {
        return "StockPurchase{" +
            "buyingPrice=" + buyingPrice +
            ", sellingPrice=" + sellingPrice +
            '}';
    }
}
