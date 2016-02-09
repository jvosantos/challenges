package com.jvosantos.challenges.jpmorgan.services;

import com.jvosantos.challenges.jpmorgan.models.Stock;
import com.jvosantos.challenges.jpmorgan.models.Trade;
import com.jvosantos.challenges.jpmorgan.models.TradeType;

import java.util.Collection;

/**
 * Provides an interface for stock information retrieval and calculation (e.g. dividends,
 * price earnings ratio) as well as trading stocks.
 * @author Vasco Santos
 */
public interface MarketExchangeService {
  /**
   * Retrieves all available stocks.
   * @return All stocks
   */
  Iterable<Stock> getAllStocks();

  /**
   * Retrieves all trades of a given stock
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @return A collection of all trades a given stock has.
   */
  Collection<Trade> getAllTradesOfStock(String stockSymbol);

  /**
   * Calculates the dividend yield of a known stock identified by it's stock symbol.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @return The dividend yield.
   */
  double calculateDividendYield(String stockSymbol);

  /**
   * Calculates the price earnings ratio of a known stock identified by it's stock symbol.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @return The price earnings ratio.
   */
  double calculatePriceEarningsRatio(String stockSymbol);

  /**
   * Purchase a number of shares of the stock represented by the stock symbol.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @param quantity The number of shares to be purchased.
   */
  void buyShares(String stockSymbol, int quantity);

  /**
   * Sells a number of shares of the stock represented by the stock symbol.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @param quantity The number of shares to be sold.
   */
  void sellShares(String stockSymbol, int quantity);

  /**
   * Records a transaction of a number of shares of the stock represented by the stock symbol.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @param quantity The number of shares to be exchanged.
   * @param tradeType The type of trade to be performed.
   */
  void recordTrade(String stockSymbol, int quantity, TradeType tradeType);

  /**
   * Retrieves the price of a stock, based on the latest transactions or the price defined in the
   * stock if there are no transactions.
   * @param stockSymbol The symbol that uniquely identifies a stock.
   * @return The price of the stock.
   */
  double getStockPrice(String stockSymbol);

  /**
   * Calculates the Global Beverage Corporation Exchange index, a stock market index whose
   * components are weighted according to the total market value of their outstanding shares.
   * @return The Global Beverage Corporation Exchange index.
   */
  double calculateGBCEAllShareIndex();
}
