package com.virtconf.paperTrader.dto;

public class StockLivePriceResponseDTO {
    private Float livePrice;
    private Float open;

    public Float getLivePrice() {
        return livePrice;
    }

    public void setLivePrice(Float livePrice) {
        this.livePrice = livePrice;
    }

    public Float getOpen() {
        return open;
    }

    public void setOpen(Float open) {
        this.open = open;
    }
}
