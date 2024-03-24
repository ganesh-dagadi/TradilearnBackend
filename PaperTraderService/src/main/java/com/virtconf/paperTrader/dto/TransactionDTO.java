package com.virtconf.paperTrader.dto;

import com.virtconf.paperTrader.model.AccountTransaction;
import com.virtconf.paperTrader.model.StockTransaction;

import java.util.List;

public class TransactionDTO {
    List<AccountTransaction> accountTransactions;
    List<StockTransaction> stockTransactions;

    public List<AccountTransaction> getAccountTransactions() {
        return accountTransactions;
    }

    public void setAccountTransactions(List<AccountTransaction> accountTransactions) {
        this.accountTransactions = accountTransactions;
    }

    public List<StockTransaction> getStockTransactions() {
        return stockTransactions;
    }

    public void setStockTransactions(List<StockTransaction> stockTransactions) {
        this.stockTransactions = stockTransactions;
    }
}
