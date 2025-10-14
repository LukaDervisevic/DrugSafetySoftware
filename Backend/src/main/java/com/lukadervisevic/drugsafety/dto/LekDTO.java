package com.lukadervisevic.drugsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lukadervisevic.drugsafety.model.Lek;
import com.lukadervisevic.drugsafety.model.LekId;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class LekDTO {

    @Column(length = 500)
    private String inn;

    @Column(length = 500)
    private String oblikIDozaLeka;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumResenjaOStavljanjuLekaUPromet;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datumVazenjaResenja;

    private String vrstaResenja;
    private String nazivLeka;
    private String rezimIzdavanjaLeka;
    private String brojResenjaOStavljanjuLekaUPromet;
    private String proizvodjac;
    private String nosilacDozvole;
    private String atc;
    private String ean;
    private String jkl;
    private String vrstaLeka;
    private String sifraProizvoda;
    private String sifraProizvodjacaUSaradnji;
    private String oblikSaradnje;
    private String sifraProizvodjaca;
    private String sifraNosiocaDozvole;

    public static Lek mapDTOToEntity(LekDTO dto) {
        Lek lek = new Lek();
        lek.setId(new LekId(
                dto.getBrojResenjaOStavljanjuLekaUPromet(),
                dto.getSifraProizvoda(),
                dto.getSifraProizvodjaca(),
                dto.getSifraNosiocaDozvole(),
                dto.getVrstaResenja()
        ));
        lek.setNazivLeka(dto.getNazivLeka());
        lek.setInn(dto.getInn());
        lek.setRezimIzdavanjaLeka(dto.getRezimIzdavanjaLeka());
        lek.setOblikIDozaLeka(dto.getOblikIDozaLeka());
        lek.setDatumResenjaOStavljanjuLekaUPromet(dto.getDatumResenjaOStavljanjuLekaUPromet());
        lek.setDatumVazenjaResenja(dto.getDatumVazenjaResenja());
        lek.setProizvodjac(dto.getProizvodjac());
        lek.setNosilacDozvole(dto.getNosilacDozvole());
        lek.setAtc(dto.getAtc());
        lek.setEan(dto.getEan());
        lek.setJkl(dto.getJkl());
        lek.setVrstaLeka(dto.getVrstaLeka());
        lek.setSifraProizvodjacaUSaradnji(dto.getSifraProizvodjacaUSaradnji());
        lek.setOblikSaradnje(dto.getOblikSaradnje());

        return lek;
    }

    public boolean DTOequalsEntity(Lek lek) {
        if (lek == null) return false;

        return
                Objects.equals(this.nazivLeka, lek.getNazivLeka()) &&
                Objects.equals(this.inn, lek.getInn()) &&
                Objects.equals(this.rezimIzdavanjaLeka, lek.getRezimIzdavanjaLeka()) &&
                Objects.equals(this.oblikIDozaLeka, lek.getOblikIDozaLeka()) &&
                Objects.equals(this.datumResenjaOStavljanjuLekaUPromet, lek.getDatumResenjaOStavljanjuLekaUPromet()) &&
                Objects.equals(this.datumVazenjaResenja, lek.getDatumVazenjaResenja()) &&
                Objects.equals(this.proizvodjac, lek.getProizvodjac()) &&
                Objects.equals(this.nosilacDozvole, lek.getNosilacDozvole()) &&
                Objects.equals(this.atc, lek.getAtc()) &&
                Objects.equals(this.ean, lek.getEan()) &&
                Objects.equals(this.jkl, lek.getJkl()) &&
                Objects.equals(this.vrstaLeka, lek.getVrstaLeka()) &&
                Objects.equals(this.sifraProizvodjacaUSaradnji, lek.getSifraProizvodjacaUSaradnji()) &&
                Objects.equals(this.oblikSaradnje, lek.getOblikSaradnje()) &&
                Objects.equals(this.sifraProizvodjaca, lek.getId().getSifraProizvodjaca()) &&
                Objects.equals(this.sifraNosiocaDozvole, lek.getId().getSifraNosiocaDozvole()) &&
                Objects.equals(this.brojResenjaOStavljanjuLekaUPromet, lek.getId().getBrojResenjaOStavljanjuLekaUPromet()) &&
                Objects.equals(this.sifraProizvoda, lek.getId().getSifraProizvoda());
    }

}
