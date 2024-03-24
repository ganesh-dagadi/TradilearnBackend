package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.dto.BuySellStockRequestDTO;
import com.virtconf.paperTrader.dto.PersonDataDTO;
import com.virtconf.paperTrader.utils.error.BadRequest;

public interface TransactionService {
    String addMoneyToAccount(PersonDataDTO user , AddMoneyRequestDTO requestData) throws Exception;
    String buyStock(PersonDataDTO user , BuySellStockRequestDTO requestData) throws BadRequest;
    String sellStock(PersonDataDTO user , BuySellStockRequestDTO requestData) throws BadRequest;
}
