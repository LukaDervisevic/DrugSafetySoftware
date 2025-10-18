package com.lukadervisevic.drugsafety.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lukadervisevic.drugsafety.dto.LekDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@NoArgsConstructor
@Data
public class Lek {
    @EmbeddedId
    @NonNull
    private LekId id;

    @Column(length = 500)
    @NonNull
    private String inn;

    @Column(length = 500)
    @NonNull
    private String oblikIDozaLeka;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private LocalDate datumResenjaOStavljanjuLekaUPromet;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NonNull
    private LocalDate datumVazenjaResenja;

    @NonNull
    private String nazivLeka;
    @NonNull
    private String rezimIzdavanjaLeka;
    @NonNull
    private String proizvodjac;
    @NonNull
    private String vrstaLeka;
    @NonNull
    private String sifraProizvodjacaUSaradnji;
    @NonNull
    private String oblikSaradnje;

    @OneToMany(mappedBy = "lek",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pismo> pisma = new ArrayList<>();


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
