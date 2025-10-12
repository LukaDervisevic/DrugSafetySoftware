package com.lukadervisevic.drugsafety.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Objava {
    @Id
    private int id;
    private LocalDate datum;
    private String naslovPisma;
    private String tekstPisma;
    private List<Pismo> pisma;
}
