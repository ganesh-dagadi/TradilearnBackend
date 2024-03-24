package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.*;

public interface AccountInfoService {
    BalanceResponseDTO getBalance(PersonDataDTO user);
    TransactionResponseDTO getTransactions(PersonDataDTO user);
    HoldingResponseDTO getHoldings(PersonDataDTO user);
    AccountInfoDTO getAccountInfo(PersonDataDTO user);
}
