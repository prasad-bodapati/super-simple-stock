package com.jmc.stock;

import com.jmc.stock.exception.StockNotFoundException;
import com.jmc.stock.model.Stock;

public interface StockData {
    Stock getData(String stockSymbol) throws StockNotFoundException;
}
