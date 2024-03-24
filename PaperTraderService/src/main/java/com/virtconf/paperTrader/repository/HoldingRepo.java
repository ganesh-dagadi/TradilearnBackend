package com.virtconf.paperTrader.repository;

import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HoldingRepo extends JpaRepository<Holding, Integer> {
    @Query(value = "SELECT h FROM Holding h WHERE h.account = ?1 AND h.symbol = ?2 AND h.isLong = ?3")
    Optional<Holding> findByAccountIdSymbolAndLong(Account acc_id , String symbol , Boolean isLong);
}
