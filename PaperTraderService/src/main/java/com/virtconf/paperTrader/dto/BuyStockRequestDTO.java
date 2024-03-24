package com.virtconf.paperTrader.dto;

public class BuyStockRequestDTO {
    private String symbol;
    private Integer qty;
    private Boolean isLong;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Boolean getIsLong() {
        return isLong;
    }

    public void setIsLong(Boolean isLong) {
        this.isLong = isLong;
    }
}
