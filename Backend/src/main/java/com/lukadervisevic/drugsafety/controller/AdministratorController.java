package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.AdministratorDTO;
import com.lukadervisevic.drugsafety.service.AdministratorService;
import com.lukadervisevic.drugsafety.util.AuthCredencials;
import com.lukadervisevic.drugsafety.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministratorController {

    @Autowired
    private AdministratorService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthCredencials authCredencials) {
        try {
            // Vracanje odgovora sa telom {token: "..."}, ukoliko ne dodje do greske
            return ResponseEntity.ok(Map.of("token",service.login(authCredencials)));
        } catch (Exception e) {
            // Obrada neuspele prijave, vracanje statusa 401 sa greskom kao porukom
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdministratorDTO adminDto) {
        try {
            // Ukoliko je registracija uspesna vrati podatke
            AdministratorDTO registeredAdmin = service.registerAdministrator(adminDto);
            return ResponseEntity.ok(registeredAdmin);
        } catch (UserAlreadyExistsException e) {
            // Ukoliko administrator vec postoji vrati odgovor sa statusom 409 i porukom
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        }
    }
}
