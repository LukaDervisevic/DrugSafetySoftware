package com.lukadervisevic.drugsafety.mapper;

import com.lukadervisevic.drugsafety.dto.AdministratorDTO;
import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.entity.Pismo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdministratorMapper {
    @Autowired
    private final PismoMapper pismoMapper;

    public AdministratorDTO toDTO(Administrator administrator) {
        if(administrator == null) return null;

        AdministratorDTO dto = new AdministratorDTO();
        dto.setKorisnickoIme(administrator.getKorisnickoIme());
        dto.setSifra(administrator.getSifra());
        dto.setEmail(administrator.getEmail());
        dto.setAktivan(administrator.isAktivan());
        dto.setToken(administrator.getToken());

        List<PismoDTO> pisma = administrator.getPisma()
                .stream().map(pismoMapper::toDto).toList();
        dto.setPisma(pisma);

        return dto;
    }

    public Administrator toEntity(AdministratorDTO dto) {
        Administrator administrator = new Administrator();
        administrator.setKorisnickoIme(dto.getKorisnickoIme());
        administrator.setEmail(dto.getEmail());
        administrator.setSifra(dto.getSifra());
        administrator.setAktivan(dto.isAktivan());
        administrator.setToken(dto.getToken());

        List<Pismo> pisma = dto.getPisma()
                .stream().map(pismoMapper::toEntity).toList();
        administrator.setPisma(pisma);

        return administrator;
    }

}
