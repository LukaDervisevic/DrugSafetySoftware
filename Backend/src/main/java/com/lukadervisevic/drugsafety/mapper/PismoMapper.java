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

        if (pismo.getLek() != null) {
            dto.setLekDto(lekMapper.toDto(pismo.getLek()));
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

        if (dto.getLekDto() != null) {
            Lek lek = lekMapper.toEntity(dto.getLekDto());
            pismo.setLek(lek);
        }

        return pismo;
    }

}
