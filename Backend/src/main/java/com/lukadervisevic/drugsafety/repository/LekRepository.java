package com.lukadervisevic.drugsafety.repository;

import com.lukadervisevic.drugsafety.model.Lek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LekRepository extends JpaRepository<Lek,String> {
}
