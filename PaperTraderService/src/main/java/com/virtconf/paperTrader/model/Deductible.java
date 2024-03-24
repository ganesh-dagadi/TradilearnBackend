package com.virtconf.paperTrader.model;

import jakarta.persistence.*;

@Entity
public class Deductible {
    @Id
    @GeneratedValue
    Integer id;
    Float amount;
    String deductibleType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDeductibleType() {
        return deductibleType;
    }

    public void setDeductibleType(String deductibleType) {
        this.deductibleType = deductibleType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
