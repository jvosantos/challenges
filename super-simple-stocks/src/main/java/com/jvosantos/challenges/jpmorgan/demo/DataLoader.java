package com.jvosantos.challenges.jpmorgan.demo;

import com.jvosantos.challenges.jpmorgan.models.Stock;
import com.jvosantos.challenges.jpmorgan.models.StockType;
import com.jvosantos.challenges.jpmorgan.repositories.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Loads data required by {@link DemoRunner}.
 *
 * Data only loaded if "demo" is one of the active profiles.
 *
 * @author Vasco Santos
 */
@Component
@Profile("demo")
@Order(value = 1)
public class DataLoader {
  private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);
  private StockRepository stockRepository;

  @Autowired
  public DataLoader(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  @PostConstruct
  public void init() {
    LOG.info("Inserting dummy stocks");
    List<Stock> stocks = new ArrayList<>();

    Random random = new Random(System.currentTimeMillis());

    stocks.add(new Stock("TEA", StockType.COMMON, 0, 0, 100, random.nextInt(100)));
    stocks.add(new Stock("POP", StockType.COMMON, 8, 0, 100, random.nextInt(100)));
    stocks.add(new Stock("ALE", StockType.COMMON, 23, 0, 60, random.nextInt(100)));
    stocks.add(new Stock("GIN", StockType.PREFERRED, 8, 2, 100, random.nextInt(100)));
    stocks.add(new Stock("JOE", StockType.COMMON, 13, 0, 250, random.nextInt(100)));

    stockRepository.save(stocks);
    LOG.info("Inserted dummy stocks");
  }

}
