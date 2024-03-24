package com.virtconf.paperTrader.model;

import jakarta.persistence.*;

@Entity
public class StockTransaction {
    @Id
    @GeneratedValue
    Integer id;
    Float unitValue;
    Integer qty;
    Boolean isBuy;
    Boolean isLong;
    String symbol;
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(Float unitValue) {
        this.unitValue = unitValue;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Boolean getBuy() {
        return isBuy;
    }

    public void setBuy(Boolean buy) {
        isBuy = buy;
    }

    public Boolean getLong() {
        return isLong;
    }

    public void setLong(Boolean aLong) {
        isLong = aLong;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
