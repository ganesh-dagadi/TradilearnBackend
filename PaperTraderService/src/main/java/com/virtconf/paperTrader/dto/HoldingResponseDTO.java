package com.virtconf.paperTrader.dto;

import com.virtconf.paperTrader.model.Holding;

import java.util.List;

public class HoldingResponseDTO {
    List<Holding> holdings;

    public List<Holding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }
}
