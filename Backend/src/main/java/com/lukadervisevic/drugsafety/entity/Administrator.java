package com.lukadervisevic.drugsafety.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
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

    private String email;

    private boolean aktivan;

    @OneToMany(mappedBy = "administrator")
    private List<Pismo> pisma = new LinkedList<>();

}
