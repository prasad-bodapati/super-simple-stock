package com.jmc.stock.impl;

import com.jmc.stock.TradeService;
import com.jmc.stock.exception.InvalidInputException;
import com.jmc.stock.exception.TradeNotFoundException;
import com.jmc.stock.model.Trade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class TradeServiceImpl implements TradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final Map<String, Trade> tradeMap = new HashMap<String, Trade>();

    public String recordATrade(Trade trade) {
        validate(trade);
        String randomUUID = UUID.randomUUID().toString();
        LOGGER.info("Recording a trade with id:" + randomUUID);
        trade.setId(randomUUID);
        tradeMap.put(randomUUID, trade);
        return randomUUID;
    }

    private void validate(Trade trade) {
        if (trade == null)
            throw new InvalidInputException("Failed to record a trade, trade can not be null");

        if (StringUtils.isEmpty(trade.getStockSymbol()))
            throw new InvalidInputException("Failed to record a trade, trade symbol can not be empty");

    }

    public List<Trade> getAllTradesAfterTheGivenTime(String stockSymbol, Date startTime) {
        LOGGER.info("Retrieving all trades after:" + startTime.toString());
        List<Trade> tradeList = new ArrayList<Trade>();
        for (Trade trade : tradeMap.values())
            if (trade.getTimestamp().compareTo(startTime) > 0 && trade.getStockSymbol().equalsIgnoreCase(stockSymbol))
                tradeList.add(trade);
        return tradeList;
    }

    public Trade get(String tradeId) {
        LOGGER.info("Retrieving trade with id:" + tradeId);
        Trade trade = tradeMap.get(tradeId);
        if (trade == null)
            throw new TradeNotFoundException("There is no trade found with ID:" + tradeId);
        return trade;
    }

    public List<Trade> listAll() {
        return new ArrayList<Trade>(tradeMap.values());
    }
}
