
package com.stock.reporter.spec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import com.stock.reporter.domain.StockTicker;
import com.stock.reporter.service.StockTickerService;

/**
 * Test cases for stock ticker
 * @date Mar 18, 2019
 *
 */
class StockTickerSpec {

	private StockTicker stockTicker = null;
	private StockTickerService stockTickerService = null;
	
	public StockTickerSpec() {
		stockTicker = new StockTicker();
		stockTickerService =  new StockTickerService();
	}
	
	@DisplayName("Insert StockTicker data")
	@Test
	void insertData() {
		stockTicker.setTickerId(100);
		stockTicker.setSymbol("MSFT");
		stockTicker.setName("Microsoft");
		int result = stockTickerService.insert(stockTicker);
		
		assertTrue(() -> result == 1, "Insert opoeration failed for ticker id:" + stockTicker.getTickerId());
	}
	
	@DisplayName("Delete StockTicker data")
	@Test
	void deleteData() {
		
	}

}