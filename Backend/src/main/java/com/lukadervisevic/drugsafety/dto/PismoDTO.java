package com.lukadervisevic.drugsafety.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PismoDTO {
    private int id;
    private LocalDate datum;
    private String naslovPisma;
    private String tekstPisma;
    private String dokumentUrl;
    private LekDTO lekDto;

}
