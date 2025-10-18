package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.entity.Administrator;
import com.lukadervisevic.drugsafety.entity.Pismo;
import com.lukadervisevic.drugsafety.mapper.PismoMapper;
import com.lukadervisevic.drugsafety.repository.AdministratorRepository;
import com.lukadervisevic.drugsafety.repository.PismoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
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
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PismoService {

    @Autowired
    private PismoRepository repo;

    @Autowired
    private AdministratorRepository adminRepo;

    @Autowired
    private PismoMapper mapper;

    private final Path rootLocation = Paths.get("Backend","documents");

    public PismoDTO createPismo(PismoDTO pismoDTO, MultipartFile pdf) throws IOException {
        Pismo pismo = mapper.toEntity(pismoDTO);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        pismo.setAdministrator(admin);

        if(!pdf.isEmpty()) {
            String filename = pismo.getNaslovPisma()+"_"+pismo.getId()+".pdf";
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            pdf.transferTo(targetPath.toFile());
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
        List<Pismo> pisma = repo.findAll();
        return pisma.stream().map(pismo -> mapper.toDto(pismo)).toList();
    }

    public Pismo updatePismo(Integer id, PismoDTO dto, MultipartFile pdf) throws IOException {
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji sa zadatim id-jem"));

        pismo.setNaslovPisma(dto.getNaslovPisma());
        pismo.setDatum(dto.getDatum());
        pismo.setTekstPisma(dto.getTekstPisma());

        if (pdf != null && !pdf.isEmpty()) {
            String filename = pismo.getNaslovPisma()+"_"+pismo.getId()+".pdf";
            Path targetPath = rootLocation.resolve(filename);
            Files.createDirectories(rootLocation);
            pdf.transferTo(targetPath.toFile());
            pismo.setDokumentUrl(filename);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Administrator admin = adminRepo.findByKorisnickoIme(username)
                .orElseThrow(() -> new RuntimeException("Administrator nije pronadjen"));
        pismo.setAdministrator(admin);

        return repo.save(pismo);
    }

    public void deletePismo(Integer id) {
        Pismo pismo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pismo ne postoji zadatim id-jem"));
        try {
            Path file = rootLocation.resolve(pismo.getDokumentUrl());
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.err.println("Neuspešno brisanje fajla: " + e.getMessage());
        }

        repo.delete(pismo);
    }

    public Resource loadPdf(Integer id) {
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
}
