package com.lukadervisevic.drugsafety.model;

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
    private String brojResenjaOStavljanjuLekaUPromet;
    @Column(length = 20)
    private String sifraProizvoda;
    @Column(length = 20)
    private String sifraProizvodjaca;
    @Column(length = 20)
    private String sifraNosiocaDozvole;
    @Column(length = 20)
    private String vrstaResenja;
}
