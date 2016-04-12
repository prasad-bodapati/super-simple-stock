package com.jmc.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeNotFoundException.class);

    public TradeNotFoundException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
