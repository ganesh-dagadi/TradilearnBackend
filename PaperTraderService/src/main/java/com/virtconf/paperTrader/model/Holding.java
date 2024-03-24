package com.virtconf.paperTrader.model;

import jakarta.persistence.*;

@Entity
public class Holding {
    @Id
    @GeneratedValue
    Integer id;
    String symbol;
    Integer qty;
    Float unitVal;
    Boolean isLong;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Float getUnitVal() {
        return unitVal;
    }

    public void setUnitVal(Float unitVal) {
        this.unitVal = unitVal;
    }

    public Boolean getIsLong() {
        return isLong;
    }

    public Account getAccountId() {
        return account;
    }

    public void setIsLong(Boolean aLong) {
        isLong = aLong;
    }
    public void setAccountId(Account accountId) {
        this.account = accountId;
    }

}
