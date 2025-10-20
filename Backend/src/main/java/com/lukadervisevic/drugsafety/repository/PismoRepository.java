package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.entity.Pismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PismoRepository extends JpaRepository<Pismo,Integer> {
    List<Pismo> findByDeletedFalse();

    @Query("SELECT p FROM Pismo p JOIN p.lekovi l WHERE LOWER(l.nazivLeka) LIKE LOWER(CONCAT(:nazivLeka, '%'))")
    List<Pismo> findByLek_NazivLekaStartingWithIgnoreCase(@Param("nazivLeka") String nazivLeka);
}
