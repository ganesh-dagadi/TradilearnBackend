package com.virtconf.paperTrader.model;

import jakarta.persistence.*;

@Entity
public class AccountTransaction {
    @Id
    @GeneratedValue
    Integer id;
    Boolean isCredit;
    Float amount;
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCredit() {
        return isCredit;
    }

    public void setCredit(Boolean credit) {
        isCredit = credit;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
