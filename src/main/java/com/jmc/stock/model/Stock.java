package com.jmc.stock.model;

public class Stock {
    private String symbol;
    private String type;
    private Double lastDividend;
    private Double fixedDividend;
    private int perValue;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(Double lastDividend) {
        this.lastDividend = lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(Double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public int getPerValue() {
        return perValue;
    }

    public void setPerValue(int perValue) {
        this.perValue = perValue;
    }
}
