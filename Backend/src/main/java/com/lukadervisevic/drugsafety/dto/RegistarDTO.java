package com.lukadervisevic.drugsafety.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistarDTO {
    private String datumIVremeKreiranjaPregleda;
    private List<LekDTO> lekovi;
}
