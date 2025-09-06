package com.signalement.service.impl;

import com.signalement.dao.CitizenRepository;
import com.signalement.dto.CitizenDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.mapper.CitizenMapper;
import com.signalement.service.ICitizenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;


@Service
public class CitizenServiceImpl implements ICitizenService {

    private CitizenRepository citizenRepository;
    private CitizenMapper citizenMapper;

    public CitizenServiceImpl() {
        this.citizenRepository = citizenRepository;
        this.citizenMapper = citizenMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public CitizenDto getCitizenById(Long id) {
        return citizenMapper.toCitizenDto(
                citizenRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Citoyen non trouvé avec l'id: " + id))
        );
    }

    @Transactional
    @Override
    public CitizenDto createCitizen(CitizenDto citizenDto) {
        return citizenMapper.toCitizenDto(
                citizenRepository.save(citizenMapper.fromCitizenDto(citizenDto))
        );
    }

    @Transactional
    @Override
    public CitizenDto updateCitizen(Long id, CitizenDto citizenDto) {
        return citizenRepository.findById(id).map(
                citizen -> {
                    citizenDto.setId(citizen.getId());
                    return citizenMapper.toCitizenDto(citizenRepository.save(citizenMapper.fromCitizenDto(citizenDto)));
                }
        ).orElseThrow(() -> new EntityNotFoundException("Citoyen non trouvé avec l'id: " + id));
    }

    @Override
    @Transactional
    public void deleteCitizen(Long id) {
        try {
            citizenRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Erreur lors de la suppression du citoyen avec l'id: " + id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<CitizenDto> getAllCitizens() {
        return StreamSupport.stream(citizenRepository.findAll().spliterator(), false)
                .map(citizenMapper::toCitizenDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CitizenDto findByEmail(String email) {
        return citizenMapper.toCitizenDto(
                citizenRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Citoyen non trouvé avec l'email: " + email))
        );
    }
}
