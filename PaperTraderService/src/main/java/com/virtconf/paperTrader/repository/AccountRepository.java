package com.virtconf.paperTrader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.virtconf.paperTrader.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account , Integer> {
    Optional<Account> findByPersonId(Integer personId);
}
