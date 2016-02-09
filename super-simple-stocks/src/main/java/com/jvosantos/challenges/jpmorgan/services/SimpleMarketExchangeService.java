package com.jvosantos.challenges.jpmorgan.services;

import com.jvosantos.challenges.jpmorgan.models.Stock;
import com.jvosantos.challenges.jpmorgan.models.Trade;
import com.jvosantos.challenges.jpmorgan.models.TradeType;
import com.jvosantos.challenges.jpmorgan.repositories.StockRepository;
import com.jvosantos.challenges.jpmorgan.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

/**
 * A simple implementation of {@link MarketExchangeService}.
 * @author Vasco Santos
 */
@Service
public class SimpleMarketExchangeService implements MarketExchangeService {

  private TradeRepository tradeRepository;
  private StockRepository stockRepository;

  @Autowired
  public SimpleMarketExchangeService(TradeRepository tradeRepository, StockRepository stockRepository) {
    this.tradeRepository = tradeRepository;
    this.stockRepository = stockRepository;
  }

  @Override
  public Iterable<Stock> getAllStocks() {
    return stockRepository.findAll();
  }

  @Override
  public Collection<Trade> getAllTradesOfStock(String stockSymbol) {
    return tradeRepository.findByStockSymbol(stockSymbol);
  }

  /**
   * {@inheritDoc}
   * The formula used to calculate the dividend yield is given by the following:
   * If it's a common stock, the dividend yield is the last dividend over the ticker price.
   * If it's a preferred stock, the dividend yield is the fixed dividend times the par value over
   * the ticker price.
   */
  @Override
  public double calculateDividendYield(String stockSymbol) {
    Stock stock = stockRepository.findBySymbol(stockSymbol);

    double dividendYield = 0.0;

    switch(stock.getType()) {
      case COMMON:
        dividendYield = stock.getLastDividend() / getStockPrice(stock.getSymbol());
        break;
      case PREFERRED:
        dividendYield = (stock.getFixedDividend() * stock.getParValue()) / getStockPrice(stock.getSymbol());
        break;
    }

    return dividendYield;
  }

  /**
   * {@inheritDoc}
   * The formula used to calculate the price earnings is ticker price over the dividend yield.
   */
  @Override
  public double calculatePriceEarningsRatio(String stockSymbol) {
    return getStockPrice(stockSymbol) / calculateDividendYield(stockSymbol);
  }

  @Override
  public void buyShares(String stockSymbol, int quantity) {
    recordTrade(stockSymbol, quantity, TradeType.BUY);
  }

  @Override
  public void sellShares(String stockSymbol, int quantity) {
    recordTrade(stockSymbol, quantity, TradeType.SELL);
  }

  @Override
  public void recordTrade(String stockSymbol, int quantity, TradeType tradeType) {
    recordTrade(stockSymbol, quantity, tradeType, getStockPrice(stockSymbol));
  }

  private void recordTrade(String stockSymbol, int quantity, TradeType tradeType, double price) {
    Trade trade = new Trade();

    trade.setQuantity(quantity);
    trade.setStock(stockRepository.findBySymbol(stockSymbol));
    trade.setType(tradeType);
    trade.setStockPrice(price);
    trade.setTimeStamp(new Date());

    tradeRepository.save(trade);
  }

  /**
   * {@inheritDoc}
   * Only the transactions performed in the last 15 minutes will be taken into consideration when
   * calculating the stock price.
   */
  @Override
  public double getStockPrice(String stockSymbol) {
    // get time at 15 minutes ago
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MINUTE, -15);

    // get all trades of target stock in the last 15 minutes
    return getStockPrice(stockSymbol, calendar.getTime());
  }

  private double getStockPrice(String stockSymbol, Date time) {
    List<Trade> trades = tradeRepository.findByStockSymbolAndTimeStampAfter(stockSymbol, time);

    Stock stock = stockRepository.findBySymbol(stockSymbol);

    if(trades.isEmpty()) {
      // if there are no trades, the stock price is returned
      return stock.getPrice();
    } else {
      // if there are trades, calculate the stock price based on the last trades
      double tradeSum = trades.parallelStream().mapToDouble(trade -> trade.getStockPrice() * trade.getQuantity()).sum();
      double quantitySum = trades.parallelStream().mapToDouble(Trade::getQuantity).sum();

      stock.setPrice(tradeSum / quantitySum);

      // update the stock price
      stockRepository.save(stock);

      return stock.getPrice();
    }
  }

  /**
   * The formula used to calculate the GBCE index is given by the geometric mean of prices for all
   * stocks, i.e., the product of all stock prices raised to the power of one over the number of
   * stocks.
   */
  @Override
  public double calculateGBCEAllShareIndex() {
    Iterable<Stock> stocks = stockRepository.findAll();

    // product of all prices
    double priceProduct = StreamSupport.stream(stocks.spliterator(), true)
                            .mapToDouble(stock -> getStockPrice(stock.getSymbol()))
                            .reduce((left, right) -> left * right).getAsDouble();

    long numberOfStocks = StreamSupport.stream(stocks.spliterator(), true).count();

    // the nth root of a number with n > 0 is equal to priceProduct ^ (1/numberOfStocks)
    return Math.pow(priceProduct, 1.0 / numberOfStocks);
  }

}
