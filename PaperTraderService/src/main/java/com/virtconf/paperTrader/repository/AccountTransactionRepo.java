package com.virtconf.paperTrader.repository;

import com.virtconf.paperTrader.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, Integer> {

}
