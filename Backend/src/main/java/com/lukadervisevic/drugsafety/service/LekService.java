package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.dto.RegistarDTO;
import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.LekId;
import com.lukadervisevic.drugsafety.mapper.LekMapper;
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
    private LekMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAndSave(){
        // Url Alims API-ja
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        // Slanje GET zahteva za registrom
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        // Provera sadrzaja registra
        if(registarDTO.isEmpty() || registarDTO.get().getLekovi() == null) return;

        // Kreiranje liste lekova na osnovu dobijenog registra
        List<Lek> lekovi = registarDTO.get().getLekovi().stream()
                .map(dto -> mapper.toEntity(dto)).toList();
        // Cuvanje/Azuriranje lekova u bazi
        repo.saveAll(lekovi);
    }
    //Kreiranje CronJob-a koji se pokrece svakog dana u 2:10 ujutru
    @Scheduled(cron = "0 10 2 * * *")
    // Anotacija koja navodi da je metoda Transakciona
    @Transactional
    public void sync() {
        System.out.println("Method syncing");
        // Vracanje postojecih lekova iz baze
        List<Lek> lekoviDB = repo.findAll();
        // Url Alims API-ja
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        // Slanje GET zahteva za registrom
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        // Provera sadrzaja registra
        if(registarDTO.isEmpty()) return;
        // Kreiranje liste objekata lekDTO iz registra
        List<LekDTO> lekoviAPI = registarDTO.get().getLekovi();
        // Kreiranje recnika lekova baze na osnovu slozenog id-ja radi brze pretrage
        Map<LekId,Lek> dbLekoviMap = lekoviDB.stream()
                .collect(Collectors.toMap(Lek::getId,lek -> lek));

        for (LekDTO lekAPI : lekoviAPI) {
            // Izvlacenje slozenog id-ja leka na osnovu lekDTO
            LekId lekId = mapper.getLekId(lekAPI);
            // Vracanje leka iz recnika na osnovu prethodnog kljuca
            Lek lekDB = dbLekoviMap.get(lekId);

            // Ako lek ne postoji dodati lek iz API-ja u bazu i logovati
            if(lekDB == null) {
                Lek mappedLek = mapper.toEntity(lekAPI);
                repo.save(mappedLek);
                log.info("New lek saved {}",mappedLek);

                // Ukoliko lekDB postoji onda proveriti da li se lekovi poklapaju po
                // atributitma ukoliko ne onda azurirati lek
            }else if(!lekAPI.DTOequalsEntity(lekDB)) {
                // Azuriranje vrednosti leka iz baze,
                lekDB.sync(lekAPI);
                // Azuriranje leka u bazi
                repo.save(lekDB);
                // Logovanje azuriranja
                log.info("Updated lek from {} to {}",lekDB,lekAPI);
            }
        }
        System.out.println("End of syncing");
    }

    public List<LekDTO> getAllLekovi() {
        // Vracanje svih lekova iz baze
        List<Lek> lekovi = repo.findAll();
        // Vracanje liste mapiranih dto-ova
        return lekovi.stream().map(lek -> mapper.toDto(lek)).toList();
    }

    public List<LekDTO> findLekoviByNazivLekaStartingWith(String naziv) {
        // Pretraga lekova sa parametrom naziv
        List<Lek> lekovi =  repo.findByNazivLekaStartingWith(naziv);
        // Vracanje liste mapiranih dto-ova
        return lekovi.stream().map(lek -> mapper.toDto(lek)).toList();
    }
}
