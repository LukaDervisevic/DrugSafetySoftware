package com.lukadervisevic.drugsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class AdministratorDTO {
    private String korisnickoIme;
    private String email;
    private String sifra;
    private boolean aktivan;
    private List<PismoDTO> pisma = new LinkedList<>();

}
