package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.service.PismoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pisma")
@CrossOrigin(origins = "http://localhost:5173")
public class PismoController {

    @Autowired
    private final PismoService service;

    @GetMapping("/{id}")
    public ResponseEntity<PismoDTO> getPismo(@RequestParam Integer id){
        PismoDTO pismoDTO = service.getPismo(id);
        return ResponseEntity.ok(pismoDTO);
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getPdf(@RequestParam Integer id) {
        Resource pdf = service.loadPdf(id);
        return ResponseEntity.ok(pdf);
    }

    @PostMapping
    public ResponseEntity<PismoDTO> createPismo(@RequestBody PismoDTO dto) {
        PismoDTO pismoDTO = service.addPismo(dto);
        return ResponseEntity.ok(pismoDTO);
    }



}
