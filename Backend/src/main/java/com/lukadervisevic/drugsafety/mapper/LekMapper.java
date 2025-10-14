package com.lukadervisevic.drugsafety.mapper;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.model.Lek;
import com.lukadervisevic.drugsafety.model.LekId;
import org.springframework.stereotype.Component;

@Component
public class LekMapper {
    public LekDTO toDto(Lek lek) {
        if (lek == null) return null;

        LekDTO dto = new LekDTO();

        dto.setBrojResenjaOStavljanjuLekaUPromet(lek.getId().getBrojResenjaOStavljanjuLekaUPromet());
        dto.setSifraProizvoda(lek.getId().getSifraProizvoda());
        dto.setSifraProizvodjaca(lek.getId().getSifraProizvodjaca());
        dto.setSifraNosiocaDozvole(lek.getId().getSifraNosiocaDozvole());
        dto.setVrstaResenja(lek.getId().getVrstaResenja());
        dto.setAtc(lek.getId().getAtc());
        dto.setEan(lek.getId().getEan());
        dto.setJkl(lek.getId().getJkl());
        dto.setNosilacDozvole(lek.getId().getNosilacDozvole());

        dto.setInn(lek.getInn());
        dto.setOblikIDozaLeka(lek.getOblikIDozaLeka());
        dto.setDatumResenjaOStavljanjuLekaUPromet(lek.getDatumResenjaOStavljanjuLekaUPromet());
        dto.setDatumVazenjaResenja(lek.getDatumVazenjaResenja());
        dto.setNazivLeka(lek.getNazivLeka());
        dto.setRezimIzdavanjaLeka(lek.getRezimIzdavanjaLeka());
        dto.setProizvodjac(lek.getProizvodjac());
        dto.setVrstaLeka(lek.getVrstaLeka());
        dto.setSifraProizvodjacaUSaradnji(lek.getSifraProizvodjacaUSaradnji());
        dto.setOblikSaradnje(lek.getOblikSaradnje());

        return dto;
    }

    public Lek toEntity(LekDTO dto) {
        if (dto == null) return null;

        Lek lek = new Lek();
        LekId id = getLekId(dto);
        lek.setId(id);

        lek.setInn(dto.getInn());
        lek.setOblikIDozaLeka(dto.getOblikIDozaLeka());
        lek.setDatumResenjaOStavljanjuLekaUPromet(dto.getDatumResenjaOStavljanjuLekaUPromet());
        lek.setDatumVazenjaResenja(dto.getDatumVazenjaResenja());
        lek.setNazivLeka(dto.getNazivLeka());
        lek.setRezimIzdavanjaLeka(dto.getRezimIzdavanjaLeka());
        lek.setProizvodjac(dto.getProizvodjac());
        lek.setVrstaLeka(dto.getVrstaLeka());
        lek.setSifraProizvodjacaUSaradnji(dto.getSifraProizvodjacaUSaradnji());
        lek.setOblikSaradnje(dto.getOblikSaradnje());
        
        return lek;
    }

    private static LekId getLekId(LekDTO dto) {
        LekId id = new LekId();
        id.setBrojResenjaOStavljanjuLekaUPromet(dto.getBrojResenjaOStavljanjuLekaUPromet());
        id.setSifraProizvoda(dto.getSifraProizvoda());
        id.setSifraProizvodjaca(dto.getSifraProizvodjaca());
        id.setSifraNosiocaDozvole(dto.getSifraNosiocaDozvole());
        id.setVrstaResenja(dto.getVrstaResenja());
        id.setAtc(dto.getAtc());
        id.setEan(dto.getEan());
        id.setJkl(dto.getJkl());
        id.setNosilacDozvole(dto.getNosilacDozvole());
        return id;
    }
}
