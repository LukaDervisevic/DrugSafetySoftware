package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.AdministratorDTO;
import com.lukadervisevic.drugsafety.service.AdministratorService;
import com.lukadervisevic.drugsafety.util.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdministratorController {

    @Autowired
    private AdministratorService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdministratorDTO adminDto) {
        try{
            return ResponseEntity.ok(service.loginAdministrator(adminDto));
        }catch(BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("message",e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdministratorDTO adminDto, BindingResult bindingResult) {
        System.out.println("Metoda register okinuta");
        if(bindingResult.hasErrors())
        {
            for(FieldError fieldError : bindingResult.getFieldErrors()) {

            }
        }


        try{
            AdministratorDTO registeredAdmin = service.registerAdministrator(adminDto);
            return ResponseEntity.ok(registeredAdmin);
        }catch(UserAlreadyExistsException e) {
            return ResponseEntity.status(409).body(Map.of("message",e.getMessage()));
        }
    }
}
