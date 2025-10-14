package com.lukadervisevic.drugsafety.model;

import java.io.File;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Entity
@Getter
@Setter
public class Pismo {
    @Id
    private int id;
    private LocalDate datum;
    private String naslovPisma;
    private String tekstPisma;
    private File dokument;
}
