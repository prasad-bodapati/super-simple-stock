package com.jmc.stock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jmc.stock.exception.StockNotFoundException;
import com.jmc.stock.model.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class SampleStockData implements StockData {
    private final String SAMPLE_DATA_FILE_NAME = "sample-data.json";
    private List<Stock> sampleStockList;
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleStockData.class);

    public SampleStockData() {
        loadSampleData();
    }

    private void loadSampleData() {
        final Reader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream(SAMPLE_DATA_FILE_NAME));
        Type collectionType = new TypeToken<List<Stock>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        sampleStockList = gson.fromJson(reader, collectionType);
    }

    public Stock getData(String stockSymbol) throws StockNotFoundException {
        for (Stock stock : sampleStockList) {
            if (stock.getSymbol().equalsIgnoreCase(stockSymbol))
                return stock;
        }
        throw new StockNotFoundException("The given stock with symbol:" + stockSymbol + " is not found in the database");
    }
}
