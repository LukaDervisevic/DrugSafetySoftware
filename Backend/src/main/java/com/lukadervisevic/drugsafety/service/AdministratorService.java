package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.AdministratorDTO;
import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.mapper.AdministratorMapper;
import com.lukadervisevic.drugsafety.repository.AdministratorRepository;
import com.lukadervisevic.drugsafety.util.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;


@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class AdministratorService {

    @Autowired
    private AdministratorRepository repo;

    @Autowired
    private AdministratorMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdministratorDTO registerAdministrator(AdministratorDTO dto) {
        Administrator admin = mapper.toEntity(dto);
        if(repo.findByKorisnickoIme(admin.getKorisnickoIme()).isPresent()) {
            throw new UserAlreadyExistsException("Administrator vec postoji sa korisnickim imenom");
        }

        admin.setSifra(passwordEncoder.encode(dto.getSifra()));
        return mapper.toDTO(repo.save(admin));
    }

    public AdministratorDTO loginAdministrator(AdministratorDTO dto) {
        Administrator admin = mapper.toEntity(dto);
        Administrator loggedAdmin = repo.findByKorisnickoIme(admin.getKorisnickoIme())
                .orElseThrow(EntityNotFoundException::new);

        if(!passwordEncoder.matches(dto.getSifra(), loggedAdmin.getSifra())) {
            throw new BadCredentialsException("Losi kredencijali za prijavu");
        }

        return mapper.toDTO(loggedAdmin);
    }

}


