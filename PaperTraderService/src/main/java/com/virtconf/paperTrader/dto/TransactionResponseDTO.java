package com.virtconf.paperTrader.dto;

import com.virtconf.paperTrader.model.AccountTransaction;
import com.virtconf.paperTrader.model.StockTransaction;

import java.util.List;

public class TransactionResponseDTO {
    TransactionDTO transactions;

    public TransactionDTO getTransaction() {
        return transactions;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transactions = transaction;
    }
}

