package org.virtuConf.demo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtuConf.demo.auth.model.Person;
import org.virtuConf.demo.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByTokenValue(String tokenValue);
}
