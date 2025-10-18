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
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        if(registarDTO.isEmpty() || registarDTO.get().getLekovi() == null) return;

        List<Lek> lekovi = registarDTO.get().getLekovi().stream()
                .map(dto -> mapper.toEntity(dto)).toList();
        repo.saveAll(lekovi);
    }

    @Scheduled(cron = "0 10 2 * * *")
    @Transactional
    public void sync() {
        System.out.println("Method syncing");
        List<Lek> lekoviDB = repo.findAll();
        String apiUrl = "https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina";
        Optional<RegistarDTO> registarDTO = Optional.ofNullable(restTemplate.getForObject(apiUrl, RegistarDTO.class));
        if(registarDTO.isEmpty()) return;
        List<LekDTO> lekoviAPI = registarDTO.get().getLekovi();

        Map<LekId,Lek> dbLekoviMap = lekoviDB.stream()
                .collect(Collectors.toMap(Lek::getId,lek -> lek));

        for (LekDTO lekAPI : lekoviAPI) {
            LekId lekId = mapper.getLekId(lekAPI);
            Lek lekDB = dbLekoviMap.get(lekId);

            if(lekDB == null) {
                Lek mappedLek = mapper.toEntity(lekAPI);
                repo.save(mappedLek);
                log.info("New lek saved {}",mappedLek);
            }else if(!lekAPI.DTOequalsEntity(lekDB)) {
                lekDB.sync(lekAPI);
                repo.save(lekDB);
                log.info("Updated lek from {} to {}",lekDB,lekAPI);
            }
        }
        System.out.println("End of syncing");
    }

    public List<LekDTO> getAllLekovi() {
        List<Lek> lekovi = repo.findAll();
        return lekovi.stream().map(lek -> mapper.toDto(lek)).toList();
    }

    public List<LekDTO> findLekoviByNazivLekaStartingWith(String naziv) {
        List<Lek> lekovi =  repo.findByNazivLekaStartingWith(naziv);
        return lekovi.stream().map(lek -> mapper.toDto(lek)).toList();
    }
}
