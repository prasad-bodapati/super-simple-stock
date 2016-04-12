package com.jmc.stock.impl;

import com.jmc.stock.StockData;
import com.jmc.stock.exception.StockNotFoundException;
import com.jmc.stock.StockCalculator;
import com.jmc.stock.TradeService;
import com.jmc.stock.model.Stock;
import com.jmc.stock.model.Trade;
import com.jmc.stock.enums.StockType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class StockCalculatorImpl implements StockCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockCalculatorImpl.class);
    private final StockData stockData;
    private final TradeService tradeService;

    public StockCalculatorImpl(StockData sampleStockData, TradeService tradeService) {
        this.stockData = sampleStockData;
        this.tradeService = tradeService;
    }

    public double getDividendForGivenStockPrice(String stockSymbol, double stockPrice) {
        LOGGER.debug("Calculating dividend for stock:" + stockSymbol + "with price:" + stockPrice);
        Stock stock = getStock(stockSymbol);
        Double dividend = 0.0;

        if (isCommonStockType(stock.getType())) {
            dividend = stock.getLastDividend() / stockPrice;
        } else if (isPreferredStockType(stock.getType())) {
            dividend = (stock.getFixedDividend() * stock.getPerValue()) / stockPrice;
        }
        return dividend;
    }

    private boolean isCommonStockType(String stockType) {
        return StockType.COMMON.toString().equalsIgnoreCase(stockType);
    }

    private boolean isPreferredStockType(String stockType) {
        return StockType.PREFERRED.toString().equalsIgnoreCase(stockType);
    }

    private Stock getStock(String stockSymbol) throws StockNotFoundException {
        return stockData.getData(stockSymbol);
    }

    public double getPERatioForGivenStockPrice(String stockSymbol, int stockPrice) {
        LOGGER.debug("Calculating P/E ratio for stock:" + stockSymbol + "with price:" + stockPrice);
        Stock stock = getStock(stockSymbol);
        return stockPrice / stock.getLastDividend();
    }

    public double getVolumeWeightedLatestStockPrice(String stockSymbol, int timePeriodInMinutes) {
        LOGGER.debug("Calculating volume weighted stock price for symbol:" +
                stockSymbol + " time period to calculate:" + timePeriodInMinutes);
        if (timePeriodInMinutes <= 0)
            timePeriodInMinutes = 15;
        Date startTime = getDateForGivenTime(timePeriodInMinutes);

        List<Trade> allTradesFromGivenTime = tradeService.getAllTradesAfterTheGivenTime(stockSymbol, startTime);
        double volumeWeightedStockPrice = calculateVolumeWeightedStockPrice(allTradesFromGivenTime);
        return volumeWeightedStockPrice;
    }

    double calculateVolumeWeightedStockPrice(List<Trade> allTrades) {
        Double sumOfProductOfStockPriceAndQuantity = 0.0;
        int totalQuantity = 0;
        for (Trade trade : allTrades) {
            totalQuantity += trade.getQuantity();
            sumOfProductOfStockPriceAndQuantity += trade.getStockPrice() * trade.getQuantity();
        }
        if (totalQuantity == 0)
            return 0.0;

        return sumOfProductOfStockPriceAndQuantity / totalQuantity;
    }

    private Date getDateForGivenTime(int timePeriodInMinutes) {
        int secondsPerMinute = 60;
        int milliSecondsPerSecond = 1000;
        int timePeriodInMilliSeconds = timePeriodInMinutes * secondsPerMinute * milliSecondsPerSecond;
        Date now = new Date();
        return new Date(now.getTime() - timePeriodInMilliSeconds);
    }
}
