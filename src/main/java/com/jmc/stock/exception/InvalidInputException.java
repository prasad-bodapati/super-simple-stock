package com.jmc.stock.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidInputException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidInputException.class);

    public InvalidInputException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
