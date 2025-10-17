package com.lukadervisevic.drugsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AdministratorDTO {
    @JsonProperty("korisnickoIme")
    private String korisnickoIme;
    @JsonProperty("email")
    private String email;
    @JsonProperty("sifra")
    private String sifra;
    @JsonProperty("aktivan")
    private boolean aktivan;
    private String token;
    private List<PismoDTO> pisma;

}
