package com.lukadervisevic.drugsafety.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lukadervisevic.drugsafety.dto.LekDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Lek {
    @EmbeddedId
    private LekId id;

    @Column(length = 500)
    private String inn;

    @Column(length = 500)
    private String oblikIDozaLeka;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumResenjaOStavljanjuLekaUPromet;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumVazenjaResenja;

    private String nazivLeka;
    private String rezimIzdavanjaLeka;
    private String proizvodjac;
    private String vrstaLeka;
    private String sifraProizvodjacaUSaradnji;
    private String oblikSaradnje;


    public void sync(LekDTO lekDTO) {
        this.id = new LekId(
                lekDTO.getBrojResenjaOStavljanjuLekaUPromet(),
                lekDTO.getSifraProizvoda(),
                lekDTO.getSifraProizvodjaca(),
                lekDTO.getSifraNosiocaDozvole(),
                lekDTO.getVrstaResenja(),
                lekDTO.getAtc(),
                lekDTO.getEan(),
                lekDTO.getInn(),
                lekDTO.getNosilacDozvole());
        this.nazivLeka = lekDTO.getNazivLeka();
        this.inn = lekDTO.getInn();
        this.rezimIzdavanjaLeka = lekDTO.getRezimIzdavanjaLeka();
        this.oblikIDozaLeka = lekDTO.getOblikIDozaLeka();
        this.datumResenjaOStavljanjuLekaUPromet = lekDTO.getDatumResenjaOStavljanjuLekaUPromet();
        this.datumVazenjaResenja = lekDTO.getDatumVazenjaResenja();
        this.proizvodjac = lekDTO.getProizvodjac();
        this.vrstaLeka = lekDTO.getVrstaLeka();
        this.sifraProizvodjacaUSaradnji = lekDTO.getSifraProizvodjacaUSaradnji();
        this.oblikSaradnje = lekDTO.getOblikSaradnje();
    }
}
