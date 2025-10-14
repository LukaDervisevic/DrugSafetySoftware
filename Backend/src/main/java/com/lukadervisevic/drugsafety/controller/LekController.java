package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.service.LekService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/lekovi")
public class LekController {

    @Autowired
    private final LekService service;

    public List<LekDTO> getAllLekovi() {
        return service.getAllLekovi();
    }

}
