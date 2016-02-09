package com.jvosantos.challenges.jpmorgan.repositories;

import com.jvosantos.challenges.jpmorgan.models.Trade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * A crud repository to access Trades.
 * @see com.jvosantos.challenges.jpmorgan.models.Trade
 * @author Vasco Santos
 */
@Repository
public interface TradeRepository extends CrudRepository<Trade, Long>{
  List<Trade> findByStockSymbolAndTimeStampAfter(String stockSymbol, Date timestamp);

  List<Trade> findByStockSymbol(String stockSymbol);
}
