package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.entity.Lek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LekRepository extends JpaRepository<Lek,String> {
    List<Lek> findByNazivLekaStartingWith(String naziv);
}
