package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;

public interface TransactionService {
    String addMoneyToAccount(AddMoneyRequestDTO requestData);
}
