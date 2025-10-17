package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findByKorisnickoIme(String korisnickoIme);
}
