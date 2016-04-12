package com.jmc.stock;

import com.jmc.stock.model.Trade;

import java.util.Date;
import java.util.List;

public interface TradeService {
    String recordATrade(Trade trade);

    List<Trade> getAllTradesAfterTheGivenTime(String stockSymbol, Date startTime);

    Trade get(String tradeId);

    List<Trade> listAll();
}
