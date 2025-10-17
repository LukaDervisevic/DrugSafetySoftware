package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.dto.RegistarDTO;
import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.LekId;
import com.lukadervisevic.drugsafety.repository.LekRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class LekService {

    @Autowired
    private LekRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAndSave(){
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        if(registarDTO.isEmpty() || registarDTO.get().getLekovi() == null) return;

        List<Lek> lekovi = registarDTO.get().getLekovi().stream().map(dto -> {
            Lek lek = new Lek();
            lek.setId(new LekId(dto.getBrojResenjaOStavljanjuLekaUPromet(),
                    dto.getSifraProizvoda(),
                    dto.getSifraProizvodjaca(),
                    dto.getSifraNosiocaDozvole(),
                    dto.getVrstaResenja(),
                    dto.getAtc(),
                    dto.getEan(),
                    dto.getJkl(),
                    dto.getNosilacDozvole()));

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
        }).toList();
        repo.saveAll(lekovi);

    }

    @Scheduled(cron = "0 10 2 * * *")
    @Transactional
    public void sync() {
        System.out.println("Method called");
        List<Lek> lekoviDB = repo.findAll();
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        if(registarDTO.isEmpty()) return;
        List<LekDTO> lekoviAPI = registarDTO.get().getLekovi();

        Map<LekId,Lek> dbLekoviMap = lekoviDB.stream()
                .collect(Collectors.toMap(Lek::getId,lek -> lek));

        for (LekDTO lekAPI : lekoviAPI) {
            LekId lekId = new LekId(
                    lekAPI.getBrojResenjaOStavljanjuLekaUPromet(),
                    lekAPI.getSifraProizvoda(),
                    lekAPI.getSifraProizvodjaca(),
                    lekAPI.getSifraNosiocaDozvole(),
                    lekAPI.getVrstaResenja(),
                    lekAPI.getAtc(),
                    lekAPI.getEan(),
                    lekAPI.getJkl(),
                    lekAPI.getNosilacDozvole()
            );

            Lek lekDB = dbLekoviMap.get(lekId);
            if(lekDB == null) {
                Lek mappedLek = LekDTO.mapDTOToEntity(lekAPI);
                repo.save(mappedLek);
                log.info("New lek saved {}",mappedLek);
            }else if(!lekAPI.DTOequalsEntity(lekDB)) {
                lekDB.sync(lekAPI);
                repo.save(lekDB);
                log.info("Updated lek from {} to {}",lekDB,lekAPI);
            }
        }
        System.out.println("End of method");
    }

    public List<LekDTO> getAllLekovi() {
        List<Lek> lekovi = repo.findAll();
        return null;
    }
}
