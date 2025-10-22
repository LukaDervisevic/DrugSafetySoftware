package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.service.LekService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/lekovi")
@CrossOrigin(origins = "http://localhost:5173")
public class LekController {

    @Autowired
    private final LekService service;

    @GetMapping
    public ResponseEntity<?> getAllLekovi() {
        // Vracanje lekova sa statusom 200
        return ResponseEntity.ok(service.getAllLekovi());
    }

    @GetMapping("/search")
    public ResponseEntity<?> getLekoviByName(@RequestParam("naziv") String naziv) {
        // Vracanje pretrazenih lekova po nazivu sa statusom 200
        return ResponseEntity.ok(service.findLekoviByNazivLekaStartingWith(naziv));
    }

}
