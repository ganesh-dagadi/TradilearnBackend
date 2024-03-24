package com.virtconf.paperTrader.repository;

import com.virtconf.paperTrader.model.Account;
import com.virtconf.paperTrader.model.Deductible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeductibleRepo extends JpaRepository<Deductible , Integer> {

    Optional<Deductible> findByAccount(Account account);
}
