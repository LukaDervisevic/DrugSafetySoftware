package com.lukadervisevic.drugsafety.util;

import com.lukadervisevic.drugsafety.entity.Administrator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AdministratorPrincipal implements UserDetails {

    private final Administrator admin;

    public AdministratorPrincipal(Administrator admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public String getPassword() {
        return admin.getSifra();
    }

    @Override
    public String getUsername() {
        return admin.getKorisnickoIme();
    }

}
