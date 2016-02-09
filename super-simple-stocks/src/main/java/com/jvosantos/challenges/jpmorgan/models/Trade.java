package com.jvosantos.challenges.jpmorgan.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Representation of a trade.
 * @author Vasco Santos
 */
@Entity
public class Trade {
  @Id
  @GeneratedValue
  private Long id;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timeStamp;

  private int quantity;

  @Enumerated(EnumType.STRING)
  private TradeType type;

  @ManyToOne
  private Stock stock;

  private double stockPrice;

  public Trade() {}

  public Trade(Date timeStamp, int quantity, TradeType type, Stock stock) {
    this.timeStamp = timeStamp;
    this.quantity = quantity;
    this.type = type;
    this.stock = stock;
  }

  public Long getId() { return id; }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public TradeType getType() {
    return type;
  }

  public void setType(TradeType type) {
    this.type = type;
  }

  public Stock getStock() {
    return stock;
  }

  public void setStock(Stock stock) {
    this.stock = stock;
  }

  public double getStockPrice() { return stockPrice; }

  public void setStockPrice(double stockPrice) { this.stockPrice = stockPrice; }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Trade trade = (Trade) o;

    return id != null ? id.equals(trade.id) : trade.id == null;

  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Trade{" +
        "id=" + id +
        ", timeStamp=" + timeStamp +
        ", quantity=" + quantity +
        ", type=" + type +
        ", stock=" + stock +
        ", stockPrice=" + stockPrice +
        '}';
  }
}
