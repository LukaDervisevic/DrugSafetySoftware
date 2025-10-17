package com.lukadervisevic.drugsafety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Entity
@Getter
@Setter
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String korisnickoIme;

    @Column(nullable = false)
    private String sifra;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean aktivan;

    @Column(nullable = false)
    private String token;

    @OneToMany(mappedBy = "administrator")
    private List<Pismo> pisma;

}
