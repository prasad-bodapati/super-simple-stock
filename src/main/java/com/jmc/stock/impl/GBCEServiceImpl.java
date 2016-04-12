package com.jmc.stock.impl;


import com.jmc.stock.GBCEService;
import com.jmc.stock.TradeService;
import com.jmc.stock.model.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GBCEServiceImpl implements GBCEService {
    private final TradeService tradeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GBCEServiceImpl.class);

    public GBCEServiceImpl(final TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public double getAllShareIndex() {
        final List<Trade> tradeList = tradeService.listAll();
        LOGGER.debug("Calculating all share index for trade list of size:" + tradeList.size());

        if (tradeList.size() == 0) {
            LOGGER.info("There are no trades to calculate the share index.");
            return 0;
        }

        double productOfAllTradePrices = 1.0;
        for (Trade trade : tradeList) {
            productOfAllTradePrices *= trade.getStockPrice();
        }
        return Math.pow(productOfAllTradePrices, 1.0 / tradeList.size());
    }
}
