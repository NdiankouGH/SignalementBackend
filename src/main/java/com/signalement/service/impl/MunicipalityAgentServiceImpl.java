package com.signalement.service.impl;

import com.signalement.dao.MunicipalAgentRepository;
import com.signalement.dto.MunicipalAgentDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.MunicipalityAgentMapper;
import com.signalement.service.IMunicipalityAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MunicipalityAgentServiceImpl implements IMunicipalityAgentService {
    private static final Logger logger = LoggerFactory.getLogger(MunicipalityAgentServiceImpl.class);
    private final MunicipalAgentRepository municipalAgentRepository;
    private final MunicipalityAgentMapper municipalAgentMapper;

    public MunicipalityAgentServiceImpl(MunicipalAgentRepository municipalAgentRepository, MunicipalityAgentMapper municipalAgentMapper) {
        this.municipalAgentRepository = municipalAgentRepository;
        this.municipalAgentMapper = municipalAgentMapper;
    }


    @Transactional(readOnly = true)
    @Override
    public List<MunicipalAgentDto> listAllMunicipalityAgents() {
        try {
            logger.info("Récupération de la liste des agents municipaux");
            return municipalAgentRepository.findAll().stream()
                    .map(municipalAgentMapper::toMunicipalAgentDto)
                    .toList();
        } catch (RequestException ex) {
            logger.error("Erreur lors de la récupération des agents municipaux: {}", ex.getMessage());
            throw new RequestException("Erreur lors de la récupération des agents municipaux", ex.getStatus());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public MunicipalAgentDto getMunicipalityAgentById(Long id) {
        logger.info("Récupération de l'agent municipal avec l'id: {}", id);
        return municipalAgentMapper.toMunicipalAgentDto(
                municipalAgentRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Agent municipal non trouvé avec l'id: " + id)
                )

        );
    }

    @Transactional
    @Override
    public MunicipalAgentDto createMunicipalityAgent(MunicipalAgentDto municipalAgentDto) {
        try {
            logger.info("Création d'un nouvel agent municipal");
            return municipalAgentMapper.toMunicipalAgentDto(
                    municipalAgentRepository.save(
                            municipalAgentMapper.fromMunicipalAgentDto(municipalAgentDto)
                    )
            );
        } catch (RequestException ex) {
            logger.error("Erreur lors de la création de l'agent municipal: {}", ex.getMessage());
            throw new RequestException("Erreur lors de la création de l'agent municipal", ex.getStatus());
        }
    }

    @Transactional
    @Override
    public MunicipalAgentDto updateMunicipalityAgent(Long id, MunicipalAgentDto municipalAgentDto) {
        logger.info("Mise à jour de l'agent municipal avec l'id: {}", id);
        return municipalAgentRepository.findById(id).map(
                municipalAgent -> {
                    municipalAgentDto.setId(municipalAgent.getId());
                    return municipalAgentMapper.toMunicipalAgentDto(
                            municipalAgentRepository.save(
                                    municipalAgentMapper.fromMunicipalAgentDto(municipalAgentDto)
                            )
                    );
                }
        ).orElseThrow(
                () -> new EntityNotFoundException("Agent municipal non trouvé avec l'id: " + id)
        );
    }

    @Transactional
    @Override
    public void deleteMunicipalityAgent(Long id) {
        try {
            logger.info("Suppression de l'agent municipal avec l'id: {}", id);
            municipalAgentRepository.deleteById(id);
        } catch (EntityNotFoundException ex) {
            logger.error("Erreur lors de la suppression de l'agent municipal: {}", ex.getMessage());
            throw new EntityNotFoundException("Erreur lors de la suppression de l'agent municipal avec l'id: " + id);
        }
    }
}
