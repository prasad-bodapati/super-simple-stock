package com.jmc.stock;


public interface StockCalculator {
    double getDividendForGivenStockPrice(String stockSymbol, double stockPrice);

    double getPERatioForGivenStockPrice(String stockSymbol, int stockPrice);

    double getVolumeWeightedLatestStockPrice(String stockSymbol, int timeInMinutes);
}
