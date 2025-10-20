package com.lukadervisevic.drugsafety.entity;

import java.time.LocalDate;
import java.util.List;

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
    @Column(columnDefinition = "TEXT")
    private String naslovPisma;
    @Column(columnDefinition = "TEXT")
    private String tekstPisma;
    private String dokumentUrl;
    private boolean deleted;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lek_pismo",
            joinColumns = @JoinColumn(name = "pismo_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "atc",referencedColumnName = "atc"),
                    @JoinColumn(name = "ean",referencedColumnName = "ean"),
                    @JoinColumn(name = "jkl",referencedColumnName = "jkl"),
                    @JoinColumn(name =  "sifra_nosioca_dozvole",referencedColumnName = "sifraNosiocaDozvole"),
                    @JoinColumn(name = "sifra_proizvoda",referencedColumnName = "sifraProizvoda"),
                    @JoinColumn(name = "sifra_proizvodjaca",referencedColumnName = "sifraProizvodjaca"),
                    @JoinColumn(name = "vrsta_resenja",referencedColumnName = "vrstaResenja")
            }
    )
    private List<Lek> lekovi;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Administrator administrator;
}
