package com.virtconf.paperTrader.dto;

public class AccountInfoDTO {
    Integer id;
    Float balance;
    Float amountAdded;
    Integer PersonId;

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
        return PersonId;
    }

    public void setPersonId(Integer personId) {
        PersonId = personId;
    }
}
