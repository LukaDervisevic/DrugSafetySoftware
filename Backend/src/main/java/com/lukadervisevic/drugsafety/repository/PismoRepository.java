package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.model.Pismo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PismoRepository extends JpaRepository<Pismo,Integer> {

}
