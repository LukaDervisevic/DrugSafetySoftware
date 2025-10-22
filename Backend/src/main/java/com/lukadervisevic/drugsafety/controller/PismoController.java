package com.lukadervisevic.drugsafety.controller;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.service.PismoService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPismoMultiPart(@RequestPart("pismo") PismoDTO dto,
                                                  @RequestPart(value = "pdf", required = false) MultipartFile pdf) {
        // Kontroler prihvata zahtev tipa form-data, gde pismo predstavlja JSON, a pdf multipartfile
        try{
            // Ukoliko je kreacija pisma uspesna, vrati podatke sa statusom 200
            PismoDTO pismoDTO = service.createPismo(dto, pdf);
            return ResponseEntity.ok(pismoDTO);
        }catch (IOException e) {
            // Ukoliko se desila greska pri cuvanju pdf dokumenta, vrati poruku sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message", "Greska pri cuvanju dokumenta"));
        }catch(Exception e) {
            // Ukoliko se desila greska pri cuvanju pisma, vrati poruku sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message", "Greska pri kreiranju pisma"));
        }
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> createPismoJSON(@RequestBody PismoDTO dto) {
        try {
            PismoDTO pismoDTO = service.createPismo(dto,null);
            return ResponseEntity.ok(pismoDTO);
        }catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Greska pri kreiranju pisma"));
        }
    }

    // Kontroler prihvata zahtev tipa form-data, gde pismo predstavlja JSON, a pdf multipartfile
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updatePismo(@PathVariable int id,
                                         @RequestPart("pismo") PismoDTO dto,
                                         @RequestPart(value = "pdf", required = false) MultipartFile pdf){
        try{
            // Ukoliko je аzuriranje pisma uspesna, vrati podatke sa statusom 200
            PismoDTO updatedPismo = service.updatePismo(id,dto,pdf);
            return ResponseEntity.ok(updatedPismo);
        }catch (IOException ex) {
            // Ukoliko se desila greska pri azuriranju pdf dokumenta, vrati poruku sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message", "Greska azuiranju dokumenta"));
        }catch(Exception ex) {
            // Ukoliko se desila greska pri azuriranju pisma, vrati poruku sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message","Greska pri azuriranju pisma"));
        }

    }

    // Kontroler prihvata GET zahtev za pisma sa neobaveznim parametrom naziv koji predstavlja naziv leka
    @GetMapping
    public ResponseEntity<?> getPisma(@RequestParam(name = "naziv", required = false) String naziv) {
        // Ukoliko naziv nije prazan pozvati metodu sa parametrom
        if(naziv != null && !naziv.isEmpty()) {
            return ResponseEntity.ok(service.getPismaByLekName(naziv));
        }
        // Ukoliko je naziv prazan pozvati generalnu metodu
        return ResponseEntity.ok(service.getPisma());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPismo(@PathVariable Integer id) {
        PismoDTO pismoDTO = service.getPismo(id);
        if(pismoDTO != null) {
            return ResponseEntity.ok(pismoDTO);
        }
        else return ResponseEntity.status(404).body(Map.of("error","Nepostojece pismo"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePismo(@PathVariable Integer id) {
        try {
            // Brisanje pisma i vracanje poruke sa statusom 200
            service.deletePismo(id);
            return ResponseEntity.ok(Map.of("message", "Pismo uspešno obrisano"));
        } catch (RuntimeException e) {
            // Vracanje poruke o gresci sa statusom 404
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Vracanje poruke o gresci sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message", "Greška pri brisanju pisma"));
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<?> getPdf(@PathVariable Integer id) {
        try {
            // Ukoliko je sistem u stanju da vrati dokument, vraca odgovor sa statusom 200 sa sadrzajem tipa application-pdf
            Resource resource = service.loadDocument(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    // Navodi pretrazivacu da otvori pdf u novom tabu
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    // U telo se stavlja pdf dokument
                    .body(resource);
        } catch (RuntimeException e) {
            // Ukoliko fajl nije pronadjen vrati poruku o gresci sa statusom 404
            return ResponseEntity.status(404).body(Map.of("message", "Fajl nije pronadjen"));
        }catch (Exception e) {
            // Ukoliko se desila greska pri preuzimanju fajla vrati poruku o gresci sa statusom 500
            return ResponseEntity.status(500).body(Map.of("message", "Greska pri preuzimanju fajla"));
        }
    }

}
