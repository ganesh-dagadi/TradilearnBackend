package com.virtconf.paperTrader.repository;

import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTransactionRepo extends JpaRepository<StockTransaction , Integer> {
    List<StockTransaction> findByAccount(Account account);
}
