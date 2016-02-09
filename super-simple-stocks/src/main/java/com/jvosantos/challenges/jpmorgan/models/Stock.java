package com.jvosantos.challenges.jpmorgan.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

/**
 * Representation of a stock.
 * @author Vasco Santos
 */
@Entity
public class Stock {
  @Id
  private String symbol;

  @Enumerated(EnumType.STRING)
  private StockType type;

  private double lastDividend;

  private double fixedDividend;

  private double parValue;

  private double price;

  public Stock() {
  }

  public Stock(String symbol, StockType type, double lastDividend, double fixedDividend,
      double parValue, double price) {
    this.symbol = symbol;
    this.type = type;
    this.lastDividend = lastDividend;
    this.fixedDividend = fixedDividend;
    this.parValue = parValue;
    this.price = price;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public StockType getType() {
    return type;
  }

  public void setType(StockType type) {
    this.type = type;
  }

  public double getLastDividend() {
    return lastDividend;
  }

  public void setLastDividend(double lastDividend) {
    this.lastDividend = lastDividend;
  }

  public double getFixedDividend() {
    return fixedDividend;
  }

  public void setFixedDividend(double fixedDividend) {
    this.fixedDividend = fixedDividend;
  }

  public double getParValue() {
    return parValue;
  }

  public void setParValue(double parValue) {
    this.parValue = parValue;
  }

  public double getPrice() { return price; }

  public void setPrice(double price) { this.price = price; }

  /**
   * A stock is equal to another object if the object is a stock and if it's symbols are equal.
   * @param o The object to be compared to this stock.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Stock stock = (Stock) o;

    return symbol.equals(stock.symbol);

  }

  @Override public int hashCode() {
    return symbol.hashCode();
  }

  @Override
  public String toString() {
    return "Stock{" +
        "symbol='" + symbol + '\'' +
        ", type=" + type +
        ", lastDividend=" + lastDividend +
        ", fixedDividend=" + fixedDividend +
        ", parValue=" + parValue +
        ", price=" + price +
        '}';
  }
}
