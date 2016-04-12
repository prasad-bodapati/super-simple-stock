package com.jmc.stock;

import com.jmc.stock.enums.TradeType;
import com.jmc.stock.exception.StockNotFoundException;
import com.jmc.stock.impl.StockCalculatorImpl;
import com.jmc.stock.impl.TradeServiceImpl;
import com.jmc.stock.model.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StockCalculatorImplTest {
    private StockCalculator stockCalculator;
    private StockData sampleStockData;
    private TradeService tradeService;

    @Before
    public void setUp() throws Exception {
        sampleStockData = new SampleStockData();
        tradeService = new TradeServiceImpl();
        stockCalculator = new StockCalculatorImpl(sampleStockData, tradeService);
    }

    @Test
    public void canCalculateCommonDividendForGivenStockPrice_whenLastDividendIsZero() {
        int stockPrice = 100;
        Double actualDividend = stockCalculator.getDividendForGivenStockPrice("TEA", stockPrice);
        Double expectedDividend = 0.0;
        assertThat(actualDividend, is(expectedDividend));
    }

    @Test
    public void canCalculateCommonDividendForGivenStockPrice_whenLastDividendIsNonZero() {
        int stockPrice = 500;
        Double actualDividend = stockCalculator.getDividendForGivenStockPrice("POP", stockPrice);
        Double expectedDividend = 0.016;
        assertThat(actualDividend, is(expectedDividend));
    }

    @Test
    public void canCalculatePreferredDividendForGivenStockPrice() {
        int stockPrice = 200;
        Double actualDividend = stockCalculator.getDividendForGivenStockPrice("GIN", stockPrice);
        Double expectedDividend = 1.0;
        assertThat(actualDividend, is(expectedDividend));
    }

    @Test
    public void canCalculatePERatioPerGivenStockPrice() {
        int stockPrice = 200;
        Double actualDividend = stockCalculator.getPERatioForGivenStockPrice("GIN", stockPrice);
        Double expectedDividend = 25.0;
        assertThat(actualDividend, is(expectedDividend));
    }

    @Test
    public void canCalculateVolumeWeightedLatestStockPrice_withOneTrade() {
        Trade trade1 = new Trade();
        trade1.setQuantity(1);
        trade1.setStockPrice(1);
        trade1.setStockSymbol("GIN");
        trade1.setTradeType(TradeType.BUY);
        trade1.setTimestamp(new Date());
        tradeService.recordATrade(trade1);
        double result = stockCalculator.getVolumeWeightedLatestStockPrice("GIN", 15);

        assertThat(1.0, is(result));
    }

    @Test
    public void canCalculateVolumeWeightedLatestStockPrice_withTenTrade() {
        int noOfTrades = 10;
        recordTrades(noOfTrades);
        double result = stockCalculator.getVolumeWeightedLatestStockPrice("TEA", 15);

        assertThat(1.0, is(result));
    }


    private void recordTrades(int noOfTrades) {
        for (int i = 0; i <= noOfTrades; i++) {
            Trade trade1 = new Trade();
            trade1.setQuantity(1);
            trade1.setStockPrice(1);
            trade1.setStockSymbol("TEA");
            trade1.setTradeType(TradeType.BUY);
            trade1.setTimestamp(new Date());
            tradeService.recordATrade(trade1);
        }
    }

    @Test
    public void canCalculateVolumeWeightedLatestStockPrice_withNoTrades() {
        double result = stockCalculator.getVolumeWeightedLatestStockPrice("TEA", 15);
        assertThat(0.0, is(result));
    }
}