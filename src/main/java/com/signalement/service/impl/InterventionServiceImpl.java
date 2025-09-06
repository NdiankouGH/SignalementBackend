package com.signalement.service.impl;

import com.signalement.dao.InterventionRepository;
import com.signalement.dto.InterventionDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.InterventionMapper;
import com.signalement.service.IInterventionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class InterventionServiceImpl implements IInterventionService {

    private static final Logger logger = LoggerFactory.getLogger(InterventionServiceImpl.class);
    private final InterventionRepository interventionRepository;
    private final InterventionMapper interventionMapper;


    public InterventionServiceImpl(InterventionRepository interventionRepository, InterventionMapper interventionMapper) {
        this.interventionRepository = interventionRepository;
        this.interventionMapper = interventionMapper;
    }

    @Override
    public InterventionDto createIntervention(InterventionDto interventionDto) {
        logger.info("Creation d'une nouvelle intervention");
        try {
            return interventionMapper.toInterventionDto(
                    interventionRepository.save(
                            interventionMapper.fromInterventionDto(interventionDto)
                    )
            );
        } catch (RequestException e) {
            logger.error("Erreur lors de la création de l'intervention{}", e.getMessage());
            throw new RequestException("Erreur lors de la création de l'intervention", HttpStatus.BAD_REQUEST);

        }
    }

    @Override
    public InterventionDto getInterventionById(Long id) {
        logger.info("Get d'une nouvelle intervention");
        return interventionMapper.toInterventionDto(
                interventionRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Intervention avec id " + id + " Non trouvé")
                )
        );
    }

    @Override
    public InterventionDto updateIntervention(Long id, InterventionDto interventionDto) {
        logger.info("Update d'une nouvelle intervention");
        return interventionRepository.findById(id).map(
                intervention -> {
                    interventionDto.setId(intervention.getId());
                    return interventionMapper.toInterventionDto(
                            interventionRepository.save(
                                    interventionMapper.fromInterventionDto(interventionDto)
                            )
                    );
                }
        ).orElseThrow(() -> new EntityNotFoundException("Intervention avec id " + id + "Non trouvé"));
    }

    @Override
    public void deleteIntervention(Long id) {
        try {
            logger.info("Delete d'une nouvelle intervention");
            interventionRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la delete de intervention {}", e.getMessage());
            throw new EntityNotFoundException("Erreur lors de la suppression de l'intervention avec l'id: " + id);
        }

    }

    @Override
    public List<InterventionDto> getAllInterventions() {
        try {
            logger.info("Get all d'une nouvelle interventions");
            return StreamSupport.stream(interventionRepository.findAll().spliterator(), false)
                    .map(interventionMapper::toInterventionDto).toList();
        } catch (RequestException exception) {
            logger.error("Erreur lors du get all des interventions {}", exception.getMessage());
            throw new RequestException("Erreur lors du get all des interventions", HttpStatus.BAD_REQUEST);
        }
    }
}
