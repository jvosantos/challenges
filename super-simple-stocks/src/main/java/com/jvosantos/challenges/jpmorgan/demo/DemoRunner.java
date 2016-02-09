package com.jvosantos.challenges.jpmorgan.demo;

import com.jvosantos.challenges.jpmorgan.models.Trade;
import com.jvosantos.challenges.jpmorgan.models.TradeType;
import com.jvosantos.challenges.jpmorgan.services.MarketExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * A demo of the services provided by {@link com.jvosantos.challenges.jpmorgan.services.SimpleMarketExchangeService}.
 *
 * DemoRunner will only be executed if "demo" is in the active profiles.
 * @author Vasco Santos
 */
@Component
@Profile("demo")
@Order(value = 10)
public class DemoRunner implements CommandLineRunner{

  private static final Logger LOG = LoggerFactory.getLogger(DemoRunner.class);

  private MarketExchangeService marketExchangeService;

  @Autowired
  public DemoRunner(MarketExchangeService marketExchangeService) {
    this.marketExchangeService = marketExchangeService;
  }

  @Override
  public void run(String... args) throws Exception {
    // print existing stocks
    LOG.info("======== Available stocks ========");

    List<String> availableStocks = new ArrayList<>();

    marketExchangeService.getAllStocks().forEach(stock -> {
      availableStocks.add(stock.getSymbol());
      LOG.info(stock.toString());}
    );


    LOG.info("======== Market open for trades ========");

    Random random = new Random(System.currentTimeMillis());

    // perform a random number of parallel transactions with a minimum of 10 transactions and a maximum of 50
    IntStream.range(0, 10+random.nextInt(40)).parallel().forEach(value -> {
      // randomly select a stock to trade with
      String stockSymbol = availableStocks.get(random.nextInt(availableStocks.size()));
      // randomly select the quantity of stocks to be traded
      int quantity = 1 + random.nextInt(100);

      // randomly buy or sell
      if(random.nextBoolean()) {
        marketExchangeService.buyShares(stockSymbol, quantity);
      } else {
        marketExchangeService.sellShares(stockSymbol, quantity);
      }
    });
    LOG.info("======== Market closed for trades ========");

    LOG.info("======== Summary of all trades performed ========");
    LOG.info("       |          | # shares | # shares | highest  |  lowest  | closing  |          |           ");
    LOG.info(" Stock | # trades |   bought |   sold   |  price   |  price   |  price   | dividend | P/E Ratio ");
    LOG.info("-------+----------+----------+----------+----------+----------+----------+----------+-----------");
    availableStocks.parallelStream().forEach(stockSymbol -> {
        Collection<Trade> stockTrades = marketExchangeService.getAllTradesOfStock(stockSymbol);

      long stockTradesCount = stockTrades.stream().count();
      int stockPurchases = stockTrades.stream().filter(trade -> trade.getType() == TradeType.BUY).mapToInt(Trade::getQuantity).sum();
      int stockSales = stockTrades.stream().filter(trade -> trade.getType() == TradeType.SELL).mapToInt(Trade::getQuantity).sum();
      double maxStockPrice = stockTrades.stream().mapToDouble(Trade::getStockPrice).max().orElse(Double.NaN);
      double minStockPrice = stockTrades.stream().mapToDouble(Trade::getStockPrice).min().orElse(Double.NaN);

      LOG.info(String.format(" %5.5s | %8d | %8d | %8d | %8.3f | %8.3f | %8.3f | %8.3f | %8.3f ",
          stockSymbol, stockTradesCount, stockPurchases, stockSales, maxStockPrice, minStockPrice,
          marketExchangeService.getStockPrice(stockSymbol),
          marketExchangeService.calculateDividendYield(stockSymbol),
          marketExchangeService.calculatePriceEarningsRatio(stockSymbol)));
      }
    );

    LOG.info("======== GBCE All share Index ========");
    LOG.info(String.format(" GBCE: %f", marketExchangeService.calculateGBCEAllShareIndex()));
  }

}
