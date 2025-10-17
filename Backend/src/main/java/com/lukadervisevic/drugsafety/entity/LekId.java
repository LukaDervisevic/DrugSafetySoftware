package com.lukadervisevic.drugsafety.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LekId implements Serializable {
    // Zakljucio sam da je ovo dobitna kombinacija atributa za primarni kljuc, testirajuci povlacenja
    // objekata sa API-ja u bazu, tako da ova kombinacija vraca sve lekove kao jedinstvene
    private String brojResenjaOStavljanjuLekaUPromet;
    @Column(length = 20)
    private String sifraProizvoda;
    @Column(length = 20)
    private String sifraProizvodjaca;
    @Column(length = 20)
    private String sifraNosiocaDozvole;
    @Column(length = 20)
    private String vrstaResenja;
    @Column(length = 20)
    private String atc;
    @Column(length = 20)
    private String ean;
    @Column(length = 20)
    private String jkl;
    private String nosilacDozvole;

}
