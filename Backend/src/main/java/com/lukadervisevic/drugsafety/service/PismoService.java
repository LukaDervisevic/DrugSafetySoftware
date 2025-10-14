package com.lukadervisevic.drugsafety.service;

import com.lukadervisevic.drugsafety.dto.PismoDTO;
import com.lukadervisevic.drugsafety.mapper.PismoMapper;
import com.lukadervisevic.drugsafety.model.Pismo;
import com.lukadervisevic.drugsafety.repository.PismoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class PismoService {

    @Autowired
    private PismoRepository repo;

    @Autowired
    private final PismoMapper mapper;

    public PismoDTO addPismo(PismoDTO pismoDTO){
        return null;
    }

    public PismoDTO getPismo(Integer id) {
        return mapper.toDto(repo.getReferenceById(id));
    }

    public Resource loadPdf(Integer id) {
        return null;
    }
}
