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
        // Mapiranje objekta AdministratorDTO u AdminDTO
        Administrator admin = mapper.toEntity(dto);
        // Provera da li vec postoji administrator za korisnickim imenom
        if (repo.findByKorisnickoIme(admin.getKorisnickoIme()).isPresent()) {
            throw new UserAlreadyExistsException("Administrator vec postoji sa korisnickim imenom");
        }
        // Postavljanje atributa aktivan na true
        admin.setAktivan(true);
        // Hesiranje sifre
        admin.setSifra(passwordEncoder.encode(dto.getSifra()));
        // Vracanje mapiranog DTO objekta
        return mapper.toDTO(repo.save(admin));
    }

    public String login(AuthCredencials credencials) {
        //Kreiranje UsernamePasswordAuthenticationToken sa kredencijalima i prosledjivanje AuthenticationManager-u
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credencials.getKorisnickoIme(), credencials.getSifra()));
            // AdministratorPrincipal implementira UserDetails
            AdministratorPrincipal admin = (AdministratorPrincipal) adminUserDetailsService
                    .loadUserByUsername(credencials.getKorisnickoIme());
            // Vracanje tokena koji je generisan JWT servisom na osnovu userDetails-a
            return jwtService.generateToken(admin);
    }

}


