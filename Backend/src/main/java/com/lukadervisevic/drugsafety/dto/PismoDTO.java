package com.lukadervisevic.drugsafety.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class PismoDTO {
    private int id;
    private LocalDate datum;
    private String naslovPisma;
    private String tekstPisma;
    private String dokumentUrl;
    private LekDTO lekDto;

}
