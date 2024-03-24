package com.virtconf.paperTrader.service;

import com.virtconf.paperTrader.dto.AddMoneyRequestDTO;
import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    AccountRepository accountRepo;
    @Override
    public String addMoneyToAccount(AddMoneyRequestDTO requestData) {
        Optional<Account> accountOp = accountRepo.findByPersonId(1);
        Account account;
        if(accountOp.isEmpty()){
            account = new Account();
            account.setBalance(requestData.getAmount());
            account.setAmountAdded(requestData.getAmount());
            account.setPersonId(1);
        }else{
            account = accountOp.get();
            account.setBalance(account.getBalance() + requestData.getAmount());
            account.setAmountAdded(account.getAmountAdded() + requestData.getAmount());
        }
        accountRepo.save(account);
        return "Amount Added";
    }
}
