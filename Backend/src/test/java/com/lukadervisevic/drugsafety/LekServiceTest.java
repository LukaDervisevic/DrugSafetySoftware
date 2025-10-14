package com.lukadervisevic.drugsafety;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.model.Lek;
import com.lukadervisevic.drugsafety.repository.LekRepository;
import com.lukadervisevic.drugsafety.service.LekService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LekServiceTest {
    @Mock
    private LekRepository repo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private LekService service;

    @Test
    public void testFetchAndSave(){
        List<Lek> lekovi = new LinkedList<>();
        lekovi.add(new Lek());
        lekovi.add(new Lek());

        LekDTO lekDTO = new LekDTO();
        lekDTO.setLekovi(lekovi);

        when(restTemplate.getForObject("https://api.alims.gov.rs/RegistarLekovaHumanaMedicinaREST/registarlekovahumanamedicina", LekDTO.class))
                .thenReturn(lekDTO);

        service.fetchAndSave();
        verify(repo).saveAll(lekDTO.getLekovi());

    }
}
