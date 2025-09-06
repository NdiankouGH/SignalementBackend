package com.signalement.service.impl;

import com.signalement.dao.MunicipalityRepository;
import com.signalement.dto.MunicipalityDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.MunicipalityMapper;
import com.signalement.service.IMunicipalityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MunicipalityServiceImpl implements IMunicipalityService {

    private static final Logger logger = LoggerFactory.getLogger(MunicipalityServiceImpl.class);
    private final MunicipalityRepository municipalRepository;
    private final MunicipalityMapper municipalityMapper;

    public MunicipalityServiceImpl(MunicipalityRepository municipalRepository, MunicipalityMapper municipalityMapper) {
        this.municipalRepository = municipalRepository;
        this.municipalityMapper = municipalityMapper;
    }


    @Override
    public List<MunicipalityDto> getAllMunicipalities() {
        try {
            logger.info("Récupération de toutes les municipalités");
            return municipalRepository.findAll().stream()
                    .map(municipalityMapper::toMunicipalityDto)
                    .toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des municipalités: {}", e.getMessage());
            throw new RequestException("Erreur lors de la récupération des municipalités", e.getStatus());
        }
    }

    @Override
    public MunicipalityDto getMunicipalityById(Long id) {
        logger.info("Récupération de la municipalité avec l'ID: {}", id);
        return municipalityMapper.toMunicipalityDto(municipalRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Municipalité non trouvée avec l'ID: " + id)
        ));
    }

    @Override
    public MunicipalityDto createMunicipality(MunicipalityDto municipalityDto) {
        try {
            logger.info("Création d'une nouvelle municipalité");
            return municipalityMapper.toMunicipalityDto(
                    municipalRepository.save(
                            municipalityMapper.fromMunicipalityDto(municipalityDto)
                    )
            );
        } catch (RequestException e) {
            logger.error("Erreur lors de la création de la municipalité: {}", e.getMessage());
            throw new RequestException("Erreur lors de la création de la municipalité", e.getStatus());
        }
    }

    @Override
    public MunicipalityDto updateMunicipality(Long id, MunicipalityDto municipalityDto) {
        logger.info("Mise à jour de la municipalité avec l'ID: {}", id);
        return municipalRepository.findById(id).map(
                municipality -> {
                    municipalityDto.setId(municipality.getId());
                    return municipalityMapper.toMunicipalityDto(
                            municipalRepository.save(
                                    municipalityMapper.fromMunicipalityDto(municipalityDto)
                            )
                    );
                }
        ).orElseThrow(
                () -> new EntityNotFoundException("Municipalité non trouvée avec l'ID: " + id)
        );
    }

    @Override
    public void deleteMunicipality(Long id) {
        try {
            logger.info("Suppression de la municipalité avec l'ID: {}", id);
            municipalRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la suppression de la municipalité: {}", e.getMessage());
            throw new EntityNotFoundException("Erreur lors de la suppression de la municipalité avec l'ID: " + id);
        }
    }
}
