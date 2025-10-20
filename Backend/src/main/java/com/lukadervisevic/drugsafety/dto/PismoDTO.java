package com.lukadervisevic.drugsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
public class PismoDTO {
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datum;

    private String naslovPisma;
    private String tekstPisma;
    private String dokumentUrl;

    @JsonProperty("lekovi")
    private List<LekDTO> lekovi;

}
