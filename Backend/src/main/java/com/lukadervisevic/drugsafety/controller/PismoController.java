package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.service.PismoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pisma")
@CrossOrigin(origins = "http://localhost:5173")
public class PismoController {

    @Autowired
    private final PismoService service;

    @PostMapping
    public ResponseEntity<?> createPismo(@RequestPart("pismo") PismoDTO dto,
                                         @RequestPart("pdf") MultipartFile pdf) {
        try{
            PismoDTO pismoDTO = service.createPismo(dto, pdf);
            return ResponseEntity.ok(pismoDTO);
        }catch (IOException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Greska pri cuvanju dokumenta"));
        }catch(Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Greska pri kreiranju pisma"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getPisma() {
        return ResponseEntity.ok(service.getPisma());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PismoDTO> getPismo(@PathVariable Integer id) {
        PismoDTO pismoDTO = service.getPismo(id);
        return ResponseEntity.ok(pismoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePismo(@PathVariable Integer id) {
        try {
            service.deletePismo(id);
            return ResponseEntity.ok(Map.of("message", "Pismo uspešno obrisano"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Greška pri brisanju pisma"));
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> getPdf(@PathVariable Integer id) {
        try {
            Resource resource = service.loadPdf(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "Fajl nije pronadjen"));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Greska pri preuzimanju fajla"));
        }
    }

}
