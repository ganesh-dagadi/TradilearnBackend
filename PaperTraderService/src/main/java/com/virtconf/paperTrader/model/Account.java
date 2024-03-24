package com.virtconf.paperTrader.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    private Float balance;
    private Float amountAdded;
    private Integer personId;
    @OneToMany(mappedBy = "account")
    private List<Holding> holdings;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> accountTransactions;

    @OneToMany(mappedBy = "account")
    private List<StockTransaction> stockTransactions;

    @OneToMany(mappedBy = "account")
    private List<Deductible> deductibles;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getAmountAdded() {
        return amountAdded;
    }

    public void setAmountAdded(Float amountAdded) {
        this.amountAdded = amountAdded;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
    public List<Holding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }

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
    public List<Deductible> getDeductibles() {
        return deductibles;
    }

    public void setDeductibles(List<Deductible> deductibles) {
        this.deductibles = deductibles;
    }
}
