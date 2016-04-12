package com.jmc.stock;

import com.jmc.stock.exception.InvalidInputException;
import com.jmc.stock.impl.TradeServiceImpl;
import com.jmc.stock.model.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class TradeServiceImplTest {

    private TradeService tradeService;

    @Before
    public void setUp() throws Exception {
        tradeService = new TradeServiceImpl();
    }

    @Test
    public void canRecordATrade() throws Exception {
        Trade trade = new Trade();
        String sampleStockSymbol = "JM";
        trade.setStockSymbol(sampleStockSymbol);

        String tradeId = tradeService.recordATrade(trade);
        assertNotNull("Trade ID should not be null", tradeId);
        Trade trade1 = tradeService.get(tradeId);
        assertThat(sampleStockSymbol, is(trade1.getStockSymbol()));
    }

    @Test(expected = InvalidInputException.class)
    public void canRecordATrade_withNoStockSymbol_throwsException() throws Exception {
        Trade trade = new Trade();
        tradeService.recordATrade(trade);
    }

    @Test(expected = InvalidInputException.class)
    public void canRecordATrade_withTradeAsNull_throwsException() throws Exception {
        tradeService.recordATrade(null);
    }

    @Test
    public void canRetrieveTradesInLastMinute() throws Exception {
        Trade trade1 = new Trade();
        trade1.setStockSymbol("symbol1");
        trade1.setTimestamp(new Date());
        Trade trade2 = new Trade();
        trade2.setStockSymbol("symbol1");
        trade2.setTimestamp(new Date());

        tradeService.recordATrade(trade1);
        tradeService.recordATrade(trade2);

        //Trade before one minute
        Trade trade3 = new Trade();
        trade3.setStockSymbol("symbol1");
        int minutes = 2;
        long secondsPerMinute = 60;
        long milliSecondsPerSecond = 1000;
        long timeInMilliSecondsTwoMinutesAgo = System.currentTimeMillis() - minutes * secondsPerMinute * milliSecondsPerSecond;
        trade3.setTimestamp(new Date(timeInMilliSecondsTwoMinutesAgo));
        tradeService.recordATrade(trade3);

        long noOfMinutes = 1;
        long timeInMilliSecondsOneMinutesAgo = System.currentTimeMillis() - noOfMinutes * secondsPerMinute * milliSecondsPerSecond;
        List<Trade> tradeList = tradeService.getAllTradesAfterTheGivenTime("symbol1", new Date(timeInMilliSecondsOneMinutesAgo));
        assertThat(tradeList.size(), is(2));
    }

    @Test
    public void canListAllTrades() throws Exception {
        Trade trade1 = new Trade();
        trade1.setStockSymbol("symbol1");
        trade1.setTimestamp(new Date());
        Trade trade2 = new Trade();
        trade2.setStockSymbol("symbol1");
        trade2.setTimestamp(new Date());

        tradeService.recordATrade(trade1);
        tradeService.recordATrade(trade2);

        List<Trade> tradeList = tradeService.listAll();
        int expectedSize = 2;
        assertThat(tradeList.size(), is(expectedSize));
    }
}