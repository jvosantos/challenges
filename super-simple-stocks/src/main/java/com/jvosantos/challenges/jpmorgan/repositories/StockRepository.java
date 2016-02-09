package com.jvosantos.challenges.jpmorgan.repositories;

import com.jvosantos.challenges.jpmorgan.models.Stock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A crud repository to access Stocks.
 * @see com.jvosantos.challenges.jpmorgan.models.Stock
 * @author Vasco Santos
 */
@Repository
public interface StockRepository extends CrudRepository<Stock, String> {
  Stock findBySymbol(String stockSymbol);
}
