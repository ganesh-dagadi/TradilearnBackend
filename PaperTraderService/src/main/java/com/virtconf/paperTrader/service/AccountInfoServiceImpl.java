package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.*;
import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.model.AccountTransaction;
import com.virtconf.paperTrader.model.Holding;
import com.virtconf.paperTrader.model.StockTransaction;
import com.virtconf.paperTrader.repository.AccountRepository;
import com.virtconf.paperTrader.repository.AccountTransactionRepo;
import com.virtconf.paperTrader.repository.HoldingRepo;
import com.virtconf.paperTrader.repository.StockTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StockTransactionRepo stockTransactionRepo;
    @Autowired
    AccountTransactionRepo accountTransactionRepo;
    @Autowired
    HoldingRepo holdingsRepo;

    @Override
    public BalanceResponseDTO getBalance(PersonDataDTO user) {
        Optional<Account> accountOp = accountRepository.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        BalanceResponseDTO balanceRes = new BalanceResponseDTO();
        balanceRes.setBalance(accountOp.get().getBalance());
        return balanceRes;
    }

    @Override
    public TransactionResponseDTO getTransactions(PersonDataDTO user) {
        Optional<Account> accountOp = accountRepository.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        Account account = accountOp.get();
        TransactionResponseDTO response = new TransactionResponseDTO();
        TransactionDTO transaction = new TransactionDTO();
        List<AccountTransaction> accountTransactions = accountTransactionRepo.findByAccount(account);
        List<StockTransaction> stockTransactions = stockTransactionRepo.findByAccount(account);
        accountTransactions.forEach(transactionItem->transactionItem.setAccount(null));
        stockTransactions.forEach(transactionItem->transactionItem.setAccount(null));
        transaction.setAccountTransactions(accountTransactions);
        transaction.setStockTransactions(stockTransactions);
        response.setTransaction(transaction);
        return response;
    }

    @Override
    public HoldingResponseDTO getHoldings(PersonDataDTO user) {
        Optional<Account> accountOp = accountRepository.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        Account account = accountOp.get();
        HoldingResponseDTO response = new HoldingResponseDTO();
        List<Holding> holdings = holdingsRepo.findByAccount(account);
        holdings.forEach(holding->holding.setAccountId(null));
        response.setHoldings(holdings);
        return response;
    }

    @Override
    public AccountInfoDTO getAccountInfo(PersonDataDTO user) {
        Optional<Account> accountOp = accountRepository.findByPersonId(user.getId());
        if(accountOp.isEmpty()) throw new RuntimeException();
        Account account = accountOp.get();
        AccountInfoDTO response = new AccountInfoDTO();
        response.setBalance(account.getBalance());
        response.setAmountAdded(account.getAmountAdded());
        response.setPersonId(account.getPersonId());
        response.setId(account.getId());
        return response;
    }
}
