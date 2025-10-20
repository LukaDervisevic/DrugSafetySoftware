package com.lukadervisevic.drugsafety.mapper;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.Pismo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PismoMapper {
    @Autowired
    private final LekMapper lekMapper;

    public PismoDTO toDto(Pismo pismo) {
        if (pismo == null) return null;

        PismoDTO dto = new PismoDTO();
        dto.setId(pismo.getId());
        dto.setDatum(pismo.getDatum());
        dto.setNaslovPisma(pismo.getNaslovPisma());
        dto.setTekstPisma(pismo.getTekstPisma());
        dto.setDokumentUrl(pismo.getDokumentUrl());

        if (pismo.getLekovi() != null ) {
            dto.setLekovi(pismo.getLekovi().stream().map(lekMapper::toDto).toList());
        }

        return dto;
    }

    public Pismo toEntity(PismoDTO dto) {
        if (dto == null) return null;

        Pismo pismo = new Pismo();
        pismo.setId(dto.getId());
        pismo.setDatum(dto.getDatum());
        pismo.setNaslovPisma(dto.getNaslovPisma());
        pismo.setTekstPisma(dto.getTekstPisma());
        pismo.setDokumentUrl(dto.getDokumentUrl());

        if (dto.getLekovi() != null) {
            pismo.setLekovi(dto.getLekovi().stream().map(lekMapper::toEntity).toList());
        }

        return pismo;
    }

}
