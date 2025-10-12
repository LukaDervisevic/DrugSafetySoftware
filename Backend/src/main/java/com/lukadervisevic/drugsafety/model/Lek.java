package com.lukadervisevic.drugsafety.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Lek {
    @Id
    private int id;
    private VrstaResenja vrstaResenja;
    private String nazivLeka;
    private String inn;
    private String rezimIzdavanjaLeka;
    private String oblikIDozaLeka;
    private String brojResenjaOStavljanjuLekaUPromet;
    private LocalDate datumResenjaOStavljanjuLekaUPromet;
    private LocalDate datumVazenjaResenja;
    private String proizvodjac;
    private String nosilacDozvole;
    private String atc;
    private String ean;
    private String jkl;
    private String vrstaLeka;
    private String sifraProizvoda;
    private String sifraProizvodjacaUSaradnji;
    private String oblikSaradnje;
    private String sifraProizvodjaca;
    private String sifraNosiocaDozvole;

}
