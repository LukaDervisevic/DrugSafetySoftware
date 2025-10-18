package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.repository.AdministratorRepository;
import com.lukadervisevic.drugsafety.util.AdministratorPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Administrator> admin = repo.findByKorisnickoIme(username);
        if(admin.isEmpty()) {
            throw new UsernameNotFoundException("Nema korisnika sa zadatim korisnickim imenom");
        }else{
            return new AdministratorPrincipal(admin.get());
        }
    }
}
