package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.LekId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LekRepository extends JpaRepository<Lek, LekId> {
    List<Lek> findByNazivLekaStartingWith(String naziv);
}
