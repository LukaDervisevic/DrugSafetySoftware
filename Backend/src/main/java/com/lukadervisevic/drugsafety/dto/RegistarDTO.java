package com.lukadervisevic.drugsafety.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegistarDTO {
    private String datumIVremeKreiranjaPregleda;
    private List<LekDTO> lekovi;
}
