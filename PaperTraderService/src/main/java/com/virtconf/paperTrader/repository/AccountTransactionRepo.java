package com.virtconf.paperTrader.repository;

import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, Integer> {
    List<AccountTransaction> findByAccount(Account account);
}
