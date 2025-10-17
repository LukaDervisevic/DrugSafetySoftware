package com.lukadervisevic.drugsafety.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pismo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate datum;
    private String naslovPisma;
    private String tekstPisma;
    private String dokumentUrl;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "atc",referencedColumnName = "atc"),
            @JoinColumn(name = "broj_resenjaostavljanju_lekaupromet", referencedColumnName = "brojResenjaOStavljanjuLekaUPromet"),
            @JoinColumn(name = "ean",referencedColumnName = "ean"),
            @JoinColumn(name = "jkl",referencedColumnName = "jkl"),
            @JoinColumn(name = "nosilac_dozvole",referencedColumnName = "nosilacDozvole"),
            @JoinColumn(name =  "sifra_nosioca_dozvole",referencedColumnName = "sifraNosiocaDozvole"),
            @JoinColumn(name = "sifra_proizvoda",referencedColumnName = "sifraProizvoda"),
            @JoinColumn(name = "sifra_proizvodjaca",referencedColumnName = "sifraProizvodjaca"),
            @JoinColumn(name = "vrsta_resenja",referencedColumnName = "vrstaResenja")
    })
    private Lek lek;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Administrator administrator;
}
