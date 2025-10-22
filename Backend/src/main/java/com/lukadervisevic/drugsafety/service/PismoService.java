package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.LekDTO;
import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.entity.Lek;
import com.lukadervisevic.drugsafety.entity.LekId;
import com.lukadervisevic.drugsafety.entity.Pismo;
import com.lukadervisevic.drugsafety.mapper.PismoMapper;
import com.lukadervisevic.drugsafety.repository.AdministratorRepository;
import com.lukadervisevic.drugsafety.repository.LekRepository;
import com.lukadervisevic.drugsafety.repository.PismoRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PismoService {

    @Autowired
    private PismoRepository repo;

    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private LekRepository lekRepo;

    @Autowired
    private PismoMapper mapper;

    private final Path rootLocation = Paths.get("Backend","documents");

    public PismoDTO createPismo(PismoDTO dto, @Nullable  MultipartFile pdf) throws IOException {
        // Mapiranje PismoDTO u Pismo
        Pismo pismo = mapper.toEntity(dto);
        // uzimanje korisnickog imena administratora i vracanje Administratora iz baze u koliko postoji
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        // Postavljanje administratora u pismu
        pismo.setAdministrator(admin);

        // Kreiranje liste svih id-jeva lekova pisma
        List<LekId> lekIds = pismo.getLekovi()
                .stream().map(Lek::getId).toList();
        // Vracanje lekova iz baze po listi id-jeva
        List<Lek> lekoviDB = lekRepo.findAllById(lekIds);
        // Uzimanje id-jeva lekova iz baze i kreiranje skupa za poredjenje
        Set<LekId> lekoviDbIds = lekoviDB.stream()
                .map(Lek::getId)
                .collect(Collectors.toSet());
        // Poredjenje lista id-jeva
        for (Lek lek : pismo.getLekovi()) {
            if (!lekoviDbIds.contains(lek.getId())) {
                throw new EntityNotFoundException("Lek " + lek.getNazivLeka() + " nije pronadjen u bazi");
            }
        }
        // Generisanje pdf dokumenta u serverskom folderu documents
        if( pdf != null &&  !pdf.isEmpty()) {
            String filename = generateDocumentName(dto,pdf);
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            Files.copy(pdf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            pismo.setDokumentUrl(filename);
        }
        // Kreiranje pisma
        Pismo createdPismo = repo.save(pismo);
        // Vracanje mapiranog dto-a
        return mapper.toDto(createdPismo);
    }

    public PismoDTO getPismo(Integer id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nije pronadjeno pismo sa zadatim id-jem")));
    }

    public List<PismoDTO> getPisma() {
        List<Pismo> pisma = repo.findByDeletedFalse();
        return pisma.stream().map(pismo -> mapper.toDto(pismo)).toList();
    }

    public List<PismoDTO> getPismaByLekName(String lekName) {
            // Vracanje pisama koja imaju atribut deleted false po nazivu leka i mapiranje u listu dto-ova
            return repo.findByDeletedFalseAndLek_NazivLekaStartingWithIgnoreCase(lekName)
                    .stream()
                    .map(pismo -> mapper.toDto(pismo))
                    .toList();
    }

    public PismoDTO updatePismo(Integer id, PismoDTO dto, MultipartFile pdf) throws IOException {
        // Mapiranje PismoDTO u Pismo
        Pismo pismo = mapper.toEntity(dto);

        Pismo oldPismo = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji sa zadatim id-jem"));
        // Kreiranje liste svih id-jeva lekova pisma
        List<LekId> lekIds = pismo.getLekovi()
                .stream().map(Lek::getId).toList();
        // Vracanje lekova iz baze po listi id-jeva
        List<Lek> lekoviDB = lekRepo.findAllById(lekIds);

    // Uzimanje id-jeva lekova iz baze i kreiranje skupa za poredjenje
        Set<LekId> lekoviDbIds = lekoviDB.stream()
                .map(Lek::getId)
                .collect(Collectors.toSet());
        // Poredjenje lista id-jeva
        for (Lek lek : pismo.getLekovi()) {
            if (!lekoviDbIds.contains(lek.getId())) {
                throw new EntityNotFoundException("Lek " + lek.getNazivLeka() + " nije pronadjen u bazi");
            }
        }

        // uzimanje korisnickog imena administratora i vracanje Administratora iz baze u koliko postoji
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        // Postavljanje administratora u pismu
        pismo.setAdministrator(admin);

        // Generisanje pdf dokumenta u serverskom folderu documents
        if (pdf != null && !pdf.isEmpty()) {
            // Brisanje vec postojeceg fajla
            if(oldPismo.getDokumentUrl() != null && !oldPismo.getDokumentUrl().isEmpty()) {
                Path oldPath = rootLocation.resolve(oldPismo.getDokumentUrl());
                Files.deleteIfExists(oldPath);
            }
            // Generisanje pdf dokumenta u serverskom folderu documents i postavljanje u pismu
            String filename = generateDocumentName(dto,pdf);
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            Files.copy(pdf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            pismo.setDokumentUrl(filename);
        }
        // Vracanje mapiranog dto-a azuriranog pisma
        return mapper.toDto(repo.save(pismo));
    }

    public void deletePismo(Integer id) {
        // Pronalazenje pisma po id-ju ukoliko ga nema baca se greska
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji zadatim id-jem"));
        // Azuriranje atributa deleted na true, ne izvrsava se stvarno brisanje iz baze
        pismo.setDeleted(true);
        // Azuriranje pisma
        repo.save(pismo);
    }

    public Resource loadDocument(Integer id) {
        // Ukoliko ne postoji pismo sa zadatim id-jem vrati gresku
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nije pronadjeno pismo sa zadatim id-jem"));

        // Nalazenje putanje do fajla na osnovu url-a dokumenta
        Path file = rootLocation.resolve(pismo.getDokumentUrl()).normalize();

        try {
            // Kreiranje resursa na osnovu putanje
            Resource resource = new UrlResource(file.toUri());
            // Provera validnosti resursa
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Fajl ne moze da se procita: " + pismo.getDokumentUrl());
            }
            // Vracanje resursa
            return resource;
        } catch (Exception e) {
            throw new RuntimeException("Neuspesno ucitavanje fajla", e);
        }
    }

    public String generateDocumentName(PismoDTO dto,MultipartFile pdf) {
        String filename = null;
        if( pdf != null &&  !pdf.isEmpty()) {
            filename = dto.getLekovi().stream().map(LekDTO::getNazivLeka).
                    limit(3)
                    .collect(Collectors.joining("_"))
                    .replaceAll("\\s+", "")
                    .replaceAll("®", "")
                    .replaceAll("š", "s")
                    .replaceAll("Š", "S")
                    .replaceAll("đ", "dj")
                    .replaceAll("Đ", "Dj")
                    .replaceAll("č", "c")
                    .replaceAll("Č", "C")
                    .replaceAll("ć", "c")
                    .replaceAll("Ć", "C")
                    .replaceAll("ž", "z")
                    .replaceAll("Ž", "Z")
                    + "_" + dto.getDatum() + ".pdf";
        }
        return filename;
    }
}
