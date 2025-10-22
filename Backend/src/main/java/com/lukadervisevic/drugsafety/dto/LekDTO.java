package com.lukadervisevic.drugsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.LekId;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
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

    public static Lek toEntity(LekDTO dto) {
        Lek lek = new Lek();
        lek.setId(new LekId(
                dto.getSifraProizvoda(),
                dto.getSifraProizvodjaca(),
                dto.getSifraNosiocaDozvole(),
                dto.getVrstaResenja(),
                dto.getAtc(),
                dto.getEan(),
                dto.getJkl()
        ));
        lek.setNazivLeka(dto.getNazivLeka());
        lek.setInn(dto.getInn());
        lek.setRezimIzdavanjaLeka(dto.getRezimIzdavanjaLeka());
        lek.setOblikIDozaLeka(dto.getOblikIDozaLeka());
        lek.setDatumResenjaOStavljanjuLekaUPromet(dto.getDatumResenjaOStavljanjuLekaUPromet());
        lek.setDatumVazenjaResenja(dto.getDatumVazenjaResenja());
        lek.setProizvodjac(dto.getProizvodjac());
        lek.setVrstaLeka(dto.getVrstaLeka());
        lek.setSifraProizvodjacaUSaradnji(dto.getSifraProizvodjacaUSaradnji());
        lek.setOblikSaradnje(dto.getOblikSaradnje());

        return lek;
    }

    public boolean DTOequalsEntity(Lek lek) {
        if (lek == null) return false;
        // Jedino je izostavljen u poredjenju brojResenjaOStavljanjUPromet jer on nije bitan za kontekst zadatka
        // kreiranja pisama, u bazi postoji vise lekova koji imaju razlicite brojeveResenja iako im oba resenja vaze
        return
                Objects.equals(this.sifraProizvodjaca, lek.getId().getSifraProizvodjaca()) &&
                        Objects.equals(this.sifraNosiocaDozvole, lek.getId().getSifraNosiocaDozvole()) &&
                        Objects.equals(this.vrstaResenja, lek.getId().getVrstaResenja()) &&
                        Objects.equals(this.sifraProizvoda, lek.getId().getSifraProizvoda()) &&
                        Objects.equals(this.atc, lek.getId().getAtc()) &&
                        Objects.equals(this.ean, lek.getId().getEan()) &&
                        Objects.equals(this.jkl, lek.getId().getJkl()) &&
                        Objects.equals(this.nazivLeka, lek.getNazivLeka()) &&
                        Objects.equals(this.inn, lek.getInn()) &&
                        Objects.equals(this.rezimIzdavanjaLeka, lek.getRezimIzdavanjaLeka()) &&
                        Objects.equals(this.oblikIDozaLeka, lek.getOblikIDozaLeka()) &&
                        Objects.equals(this.datumResenjaOStavljanjuLekaUPromet, lek.getDatumResenjaOStavljanjuLekaUPromet()) &&
                        Objects.equals(this.datumVazenjaResenja, lek.getDatumVazenjaResenja()) &&
                        Objects.equals(this.proizvodjac, lek.getProizvodjac()) &&
                        Objects.equals(this.vrstaLeka, lek.getVrstaLeka()) &&
                        Objects.equals(this.sifraProizvodjacaUSaradnji, lek.getSifraProizvodjacaUSaradnji()) &&
                        Objects.equals(this.oblikSaradnje, lek.getOblikSaradnje()) &&
                        Objects.equals(this.nosilacDozvole, lek.getNosilacDozvole()) ;
    }

}
