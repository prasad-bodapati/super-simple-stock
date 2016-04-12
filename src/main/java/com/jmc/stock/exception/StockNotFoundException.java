package com.jmc.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(StockNotFoundException.class);

    public StockNotFoundException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
