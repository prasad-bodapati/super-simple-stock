package com.jmc.stock;

import com.jmc.stock.model.Trade;
import com.jmc.stock.impl.GBCEServiceImpl;
import com.jmc.stock.impl.TradeServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;

public class GBCEServiceImplTest {
    private TradeService tradeService;
    private GBCEService gbceService;

    @Before
    public void setUp() throws Exception {
        tradeService = new TradeServiceImpl();
        gbceService = new GBCEServiceImpl(tradeService);
    }

    @Test
    public void canCalculateShareIndexForTwoTrades() throws Exception {
        recordSomeTrades();
        double shareIndex = gbceService.getAllShareIndex();
        double expectedShareIndex = 31.622776601683793;
        Assert.assertThat(shareIndex, is(expectedShareIndex));
    }

    private void recordSomeTrades() {
        Trade trade1 = new Trade();
        trade1.setStockSymbol("symbol1");
        trade1.setQuantity(1);
        trade1.setStockPrice(10.0);
        Trade trade2 = new Trade();
        trade2.setStockSymbol("symbol2");
        trade2.setQuantity(10);
        trade2.setStockPrice(100.0);

        tradeService.recordATrade(trade1);
        tradeService.recordATrade(trade2);
    }

    @Test
    public void canCalculateShareIndexWhenThereAreNoTrades() throws Exception {
        double shareIndex = gbceService.getAllShareIndex();
        double expectedShareIndex = 0;
        Assert.assertThat(shareIndex, is(expectedShareIndex));
    }
}