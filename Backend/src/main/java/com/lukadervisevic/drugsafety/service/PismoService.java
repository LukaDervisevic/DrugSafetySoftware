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
        Pismo pismo = mapper.toEntity(dto);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        pismo.setAdministrator(admin);

        List<LekId> lekIds = pismo.getLekovi()
                .stream().map(Lek::getId).toList();

        List<Lek> lekoviDB = lekRepo.findAllById(lekIds);

        Set<LekId> lekoviDbIds = lekoviDB.stream()
                .map(Lek::getId)
                .collect(Collectors.toSet());

        for (Lek lek : pismo.getLekovi()) {
            if (!lekoviDbIds.contains(lek.getId())) {
                throw new EntityNotFoundException("Lek " + lek.getNazivLeka() + " nije pronadjen u bazi");
            }
        }

        if( pdf != null &&  !pdf.isEmpty()) {
            String filename = generateDocumentName(dto,pdf);
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            Files.copy(pdf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            pismo.setDokumentUrl(filename);
        }

        Pismo createdPismo = repo.save(pismo);
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
        try{
            return repo.findByLek_NazivLekaStartingWithIgnoreCase(lekName)
                    .stream()
                    .map(pismo -> mapper.toDto(pismo))
                    .toList();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public PismoDTO updatePismo(Integer id, PismoDTO dto, MultipartFile pdf) throws IOException {
        Pismo pismo = mapper.toEntity(dto);

        Pismo oldPismo = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji sa zadatim id-jem"));

        List<LekId> lekIds = pismo.getLekovi()
                .stream().map(Lek::getId).toList();

        List<Lek> lekoviDB = lekRepo.findAllById(lekIds);

        Set<LekId> lekoviDbIds = lekoviDB.stream()
                .map(Lek::getId)
                .collect(Collectors.toSet());

        for (Lek lek : pismo.getLekovi()) {
            if (!lekoviDbIds.contains(lek.getId())) {
                throw new EntityNotFoundException("Lek " + lek.getNazivLeka() + " nije pronadjen u bazi");
            }
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        pismo.setAdministrator(admin);

        if (pdf != null && !pdf.isEmpty()) {

            if(oldPismo.getDokumentUrl() != null && !oldPismo.getDokumentUrl().isEmpty()) {
                Path oldPath = rootLocation.resolve(oldPismo.getDokumentUrl());
                Files.deleteIfExists(oldPath);
            }

            String filename = generateDocumentName(dto,pdf);
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            Files.copy(pdf.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            pismo.setDokumentUrl(filename);
        }

        return mapper.toDto(repo.save(pismo));
    }

    public void deletePismo(Integer id) {
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji zadatim id-jem"));
        pismo.setDeleted(true);
//        try {
//            Path file = rootLocation.resolve(pismo.getDokumentUrl());
//            Files.deleteIfExists(file);
//        } catch (IOException e) {
//            System.err.println("Neuspešno brisanje fajla: " + e.getMessage());
//        }

        repo.save(pismo);
    }

    public Resource loadDocument(Integer id) {
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nije pronadjeno pismo sa zadatim id-jem"));

        Path file = rootLocation.resolve(pismo.getDokumentUrl()).normalize();

        try {
            Resource resource = new UrlResource(file.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Fajl ne moze da se procita: " + pismo.getDokumentUrl());
            }
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
