package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.dto.BuyStockRequestDTO;
import com.virtconf.paperTrader.dto.PersonDataDTO;
import com.virtconf.paperTrader.utils.error.BadRequest;

public interface TransactionService {
    String addMoneyToAccount(PersonDataDTO user , AddMoneyRequestDTO requestData) throws Exception;
    String buyStock(PersonDataDTO user , BuyStockRequestDTO requestData) throws BadRequest;
}
