package org.virtuConf.demo.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.virtuConf.demo.auth.model.Person;

import java.util.Optional;
@Repository
public interface PersonRepo extends JpaRepository<Person,Integer> {
    public Optional<Person> findByEmail(String email);
}
