package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.AdministratorDTO;
import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.mapper.AdministratorMapper;
import com.lukadervisevic.drugsafety.repository.AdministratorRepository;
import com.lukadervisevic.drugsafety.util.AdministratorPrincipal;
import com.lukadervisevic.drugsafety.util.AuthCredencials;
import com.lukadervisevic.drugsafety.util.UserAlreadyExistsException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminUserDetailsService adminUserDetailsService;

    @Autowired
    private JWTService jwtService;

    public AdministratorDTO registerAdministrator(AdministratorDTO dto) {
        Administrator admin = mapper.toEntity(dto);
        if (repo.findByKorisnickoIme(admin.getKorisnickoIme()).isPresent()) {
            throw new UserAlreadyExistsException("Administrator vec postoji sa korisnickim imenom");
        }

        admin.setSifra(passwordEncoder.encode(dto.getSifra()));
        return mapper.toDTO(repo.save(admin));
    }

    public String login(AuthCredencials credencials) {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credencials.getKorisnickoIme(), credencials.getSifra()));
            AdministratorPrincipal admin = (AdministratorPrincipal) adminUserDetailsService
                    .loadUserByUsername(credencials.getKorisnickoIme());

            return jwtService.generateToken(admin);
    }

}


