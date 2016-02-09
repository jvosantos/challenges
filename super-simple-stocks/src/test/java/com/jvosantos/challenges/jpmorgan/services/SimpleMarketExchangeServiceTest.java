package com.jvosantos.challenges.jpmorgan.services;

import com.jvosantos.challenges.jpmorgan.models.Stock;
import com.jvosantos.challenges.jpmorgan.models.StockType;
import com.jvosantos.challenges.jpmorgan.models.Trade;
import com.jvosantos.challenges.jpmorgan.models.TradeType;
import com.jvosantos.challenges.jpmorgan.repositories.StockRepository;
import com.jvosantos.challenges.jpmorgan.repositories.TradeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

/**
 * Unit tests of SimpleMarketExchangeServiceTest.
 * @See SimpleMarketExchangeService
 * @author Vasco Santos
 */
public class SimpleMarketExchangeServiceTest {

  StockRepository stockRepository;
  TradeRepository tradeRepository;

  MarketExchangeService marketExchangeService = new SimpleMarketExchangeService(tradeRepository, stockRepository);

  /**
   * Before each test is run, a new {@link MarketExchangeService} is instantiated with mock
   * repositories.
   */
  @Before
  public void setUp() {
    stockRepository = mock(StockRepository.class);
    tradeRepository = mock(TradeRepository.class);

    marketExchangeService = new SimpleMarketExchangeService(tradeRepository, stockRepository);
  }

  /**
   * Tests if {@link MarketExchangeService#getAllStocks()} retrieves no stocks when there are no
   * stocks.
   */
  @Test
  public void testGetAllStocksWithNoStocks() {

    when(stockRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

    Iterator<Stock> testStockIterator = marketExchangeService.getAllStocks().iterator();

    assertEquals(false, testStockIterator.hasNext());
  }

  /**
   * Tests if {@link MarketExchangeService#getAllStocks()} retrieves all stocks.
   * @throws Exception
   */
  @Test
  public void testGetAllStocks() {
    List<Stock> stocks = new ArrayList<>();

    stocks.add(new Stock("S1", StockType.COMMON, 0.0, 0.0, 0, 1));
    stocks.add(new Stock("S2", StockType.COMMON, 0.0, 0.0, 0, 1));

    when(stockRepository.findAll()).thenReturn(stocks);

    Iterator<Stock> testStockIterator = marketExchangeService.getAllStocks().iterator();

    for(Stock stock : stocks) {
      assertTrue(testStockIterator.hasNext());
      assertEquals(stock, testStockIterator.next());
    }
  }

  /**
   * Tests if {@link MarketExchangeService#getAllTradesOfStock(String)} returns trades of one stock.
   */
  @Test
  public void testGetAllTradesOfStock() {
    List<Stock> expectedStocks = new ArrayList<>();

    Stock s1 = new Stock("S1", StockType.COMMON, 0.0, 0.0, 0, 1);
    Stock s2 = new Stock("S2", StockType.COMMON, 0.0, 0.0, 0, 1);

    expectedStocks.add(s1);
    expectedStocks.add(s2);

    List<Trade> expectedTrades = new ArrayList<>();

    expectedTrades.add(new Trade(new Date(), 10, TradeType.BUY, expectedStocks.get(0)));
    expectedTrades.add(new Trade(new Date(), 100, TradeType.SELL, expectedStocks.get(0)));
    expectedTrades.add(new Trade(new Date(), 1, TradeType.BUY, expectedStocks.get(0)));
    expectedTrades.add(new Trade(new Date(), 1000, TradeType.SELL, expectedStocks.get(0)));
    expectedTrades.add(new Trade(new Date(), 5, TradeType.SELL, expectedStocks.get(1)));
    expectedTrades.add(new Trade(new Date(), 5, TradeType.SELL, expectedStocks.get(1)));
    expectedTrades.add(new Trade(new Date(), 15, TradeType.BUY, expectedStocks.get(1)));

    when(tradeRepository.findByStockSymbol("S1")).thenReturn(expectedTrades.subList(0, 4));
    when(tradeRepository.findByStockSymbol("S2")).thenReturn(expectedTrades.subList(4, 7));

    Collection<Trade> tradesOfStock = marketExchangeService.getAllTradesOfStock("S1");
    assertEquals(4, tradesOfStock.size());

    for(Trade trade : tradesOfStock) {
      assertEquals(s1, trade.getStock());
    }

    tradesOfStock = marketExchangeService.getAllTradesOfStock("S2");
    assertEquals(3, tradesOfStock.size());

    for(Trade trade : tradesOfStock) {
      assertEquals(s2, trade.getStock());
    }
  }

  /**
   * tests the dividend yield calculation of {@link SimpleMarketExchangeService#calculateDividendYield(String)}
   * with a common stock.
   */
  @Test
  public void testCalculateDividendYieldWithCommonStock() {
    Stock stock = new Stock("S1", StockType.COMMON, 10.0, 0.0, 0.0, 100.0);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);

    assertEquals(0.1, marketExchangeService.calculateDividendYield("S1"), 0.01);
  }

  /**
   * tests the dividend yield calculation of {@link SimpleMarketExchangeService#calculateDividendYield(String)}
   * with a preferred stock.
   */
  @Test
  public void testCalculateDividendYieldWithPreferredStock() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);

    assertEquals(1.0, marketExchangeService.calculateDividendYield("S1"), 0.1);
  }

  /**
   * tests the price earnings ratio calculation of {@link SimpleMarketExchangeService#calculateDividendYield(String)}
   * with a common stock.
   */
  @Test
  public void testCalculatePERatioWithCommonStock() {
    Stock stock = new Stock("S1", StockType.COMMON, 10.0, 0.0, 0.0, 100.0);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);

    assertEquals(1000.0, marketExchangeService.calculatePriceEarningsRatio("S1"), 0.1);
  }

  /**
   * tests the price earnings ratio calculation of {@link SimpleMarketExchangeService#calculateDividendYield(String)}
   * with a preferred stock.
   */
  @Test
  public void testCalculatePERatioWithPreferredStock() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);

    assertEquals(100.0, marketExchangeService.calculatePriceEarningsRatio("S1"), 0.1);
  }

  /**
   * Tests if a purchase exchange is recorded.
   */
  @Test
  public void testBuyShares() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    List<Trade> trades = new ArrayList<>();
    Trade trade = new Trade(new Date(), 10, TradeType.BUY, stock);
    trades.add(trade);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);
    when(tradeRepository.save(trade)).thenReturn(trade);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades);

    marketExchangeService.buyShares("S1", 10);
    Collection<Trade> stockTrades = marketExchangeService.getAllTradesOfStock("S1");

    assertEquals(1, stockTrades.size());

    for(Trade stockTrade : stockTrades) {
      assertEquals(stock, stockTrade.getStock());
      assertEquals(TradeType.BUY, stockTrade.getType());
      assertTrue(stockTrade.getTimeStamp().equals(trade.getTimeStamp()));
    }
  }

  /**
   * Tests if a sale exchange is recorded.
   */
  @Test
  public void testSellShares() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    List<Trade> trades = new ArrayList<>();
    Trade trade = new Trade(new Date(), 10, TradeType.SELL, stock);
    trades.add(trade);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);
    when(tradeRepository.save(trade)).thenReturn(trade);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades);

    marketExchangeService.buyShares("S1", 10);
    Collection<Trade> stockTrades = marketExchangeService.getAllTradesOfStock("S1");

    assertEquals(1, stockTrades.size());

    for(Trade stockTrade : stockTrades) {
      assertEquals(stock, stockTrade.getStock());
      assertEquals(TradeType.SELL, stockTrade.getType());
      assertTrue(stockTrade.getTimeStamp().equals(trade.getTimeStamp()));
    }
  }

  /**
   * Test if a trade is recorded.
   */
  @Test
  public void testRecordTrade() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    List<Trade> trades = new ArrayList<>();
    Trade trade = new Trade(new Date(), 10, TradeType.SELL, stock);
    trades.add(trade);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);
    when(tradeRepository.save(trade)).thenReturn(trade);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades);

    marketExchangeService.recordTrade("S1", 10, TradeType.SELL);
    Collection<Trade> stockTrades = marketExchangeService.getAllTradesOfStock("S1");

    assertEquals(1, stockTrades.size());

    for(Trade stockTrade : stockTrades) {
      assertEquals(stock, stockTrade.getStock());
      assertEquals(TradeType.SELL, stockTrade.getType());
      assertTrue(stockTrade.getTimeStamp().equals(trade.getTimeStamp()));
    }
  }

  /**
   * Test the stock price calculation of a stock without no trades.
   */
  @Test
  public void testGetStockPriceWithoutTrades() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);

    assertEquals(100.0, marketExchangeService.getStockPrice("S1"), 0.1);
  }

  /**
   * Tests the stock price calculation of a stock with trades older than 15 minutes.
   */
  @Test
  public void testGetStockPriceWithTradesOlderThan15Minutes() {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    List<Trade> trades = new ArrayList<>();
    // 900001 milliseconds = 1 millisecond + 15 minutes * 60 seconds * 1000 milliseconds
    Date timestamp = new Date(Calendar.getInstance().getTimeInMillis() - 900001);
    trades.add(new Trade(timestamp, 10, TradeType.BUY, stock));

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades);

    assertEquals(100.0, marketExchangeService.getStockPrice("S1"), 0.1);
  }

  /**
   * Tests the stock price calculation of a stock with trades in the last 15 minutes.
   */
  @Test
  public void testGetStockPriceWithTradesInTheLast15Minutes() throws Exception {
    Stock stock = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 100.0);

    List<Trade> trades = new ArrayList<>();

    // 900001 milliseconds = 1 millisecond + 15 minutes * 60 seconds * 1000 milliseconds
    Date timestamp = new Date(Calendar.getInstance().getTimeInMillis() - 900001);
    trades.add(new Trade(timestamp, 10, TradeType.BUY, stock));
    trades.add(new Trade(new Date(), 10, TradeType.BUY, stock));    // buy at 100
    trades.add(new Trade(new Date(), 10, TradeType.SELL, stock));   // sell at 100*10/10

    when(stockRepository.findBySymbol("S1")).thenReturn(stock);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades);

    // stock price = (100*10+100*10)/20 = 100
    assertEquals(100.0, marketExchangeService.getStockPrice("S1"), 0.1);
  }

  /**
   * Test the GBCE index with two stocks.
   */
  @Test
  public void testCalculateGBCEAllShareIndex() {

    List<Stock> stocks = new ArrayList<>();

    Stock stock1 = new Stock("S1", StockType.PREFERRED, 0.0, 10.0, 10.0, 10.0);
    Stock stock2 = new Stock("S2", StockType.PREFERRED, 0.0, 10.0, 10.0, 40.0);

    stocks.add(stock1);
    stocks.add(stock2);

    List<Trade> trades = new ArrayList<>();

    // 900001 milliseconds = 1 millisecond + 15 minutes * 60 seconds * 1000 milliseconds
    Date timestamp = new Date(Calendar.getInstance().getTimeInMillis() - 900001);
    trades.add(new Trade(timestamp, 10, TradeType.BUY, stock1));
    trades.add(new Trade(new Date(), 10, TradeType.BUY, stock1));
    trades.add(new Trade(new Date(), 10, TradeType.SELL, stock1));

    when(stockRepository.findBySymbol("S1")).thenReturn(stock1);
    when(stockRepository.findBySymbol("S2")).thenReturn(stock2);
    when(stockRepository.findAll()).thenReturn(stocks);
    when(tradeRepository.findByStockSymbol("S1")).thenReturn(trades.subList(0, 3));
    when(tradeRepository.findByStockSymbol("S2")).thenReturn(new ArrayList<>());

    // stock price of S1 = 10.0
    // stock price of S2 = 40.0
    // GBCE = sqrt(S1 * S2) = sqrt(10.0 * 40.0) = sqrt(400.0) = 20.0
    assertEquals(20.0, marketExchangeService.calculateGBCEAllShareIndex(), 0.1);
  }
}
