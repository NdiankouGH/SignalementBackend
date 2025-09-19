package com.signalement.service.impl;


import com.signalement.dao.ReportingRepository;
import com.signalement.dto.ReportingDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.ReportingMapper;
import com.signalement.service.IReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportingServiceImpl implements IReportingService {
    private static final Logger logger = LoggerFactory.getLogger(ReportingServiceImpl.class);
    private final ReportingRepository reportingRepository;
    private final ReportingMapper reportingMapper;

    public ReportingServiceImpl(ReportingRepository reportingRepository, ReportingMapper reportingMapper) {
        this.reportingRepository = reportingRepository;
        this.reportingMapper = reportingMapper;
    }

    @Transactional
    @Override
    public ReportingDto createReporting(ReportingDto reportingDto) {
        try {
            logger.info("Creation d'un nouveau signalement");
            return reportingMapper.toReportingDto(
                    reportingRepository.save(
                            reportingMapper.fromReportingDto(reportingDto)
                    )
            );
        } catch (RequestException e) {
            logger.error("Erreur lors de la création du signalement {}", e.getMessage());
            throw new RequestException("Erreur lors de la création du signalement", e.getStatus());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ReportingDto getReportingById(Long id) {
        logger.info("Récupération du signalement avec l'id {}", id);
        return reportingMapper.toReportingDto(
                reportingRepository.findById(id).orElseThrow(
                        () -> new RequestException("Signalement avec l'id " + id + " non trouvé", HttpStatus.NOT_FOUND)
                )
        );
    }

    @Transactional
    @Override
    public void deleteReporting(Long id) {
        try {
            logger.info("Suppression du signalement avec l'id {}", id);
            reportingRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la suppression du signalement avec l'id {}", id);
            throw new EntityNotFoundException("Erreur lors de la suppression du signalement avec l'id " + id);
        }
    }

    @Transactional
    @Override
    public ReportingDto updateReporting(Long id, ReportingDto reportingDto) {
        return reportingRepository.findById(id).map(
                reporting -> {
                    reportingDto.setId(reporting.getId());
                    return reportingMapper.toReportingDto(reportingRepository.save(reporting)
                    );
                }
        ).orElseThrow(
                () -> new EntityNotFoundException("Signalement avec l'id " + id + " non trouvé")
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReportingDto> getAllReportings() {
        try {
            logger.info("Récupération de tous les signalements");
            return reportingRepository.findAll().stream()
                    .map(reportingMapper::toReportingDto)
                    .toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des signalements {}", e.getMessage());
            throw new RequestException("Erreur lors de la récupération des signalements", e.getStatus());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReportingDto> getAllReportingsByCitizenId(Long citizenId) {
        try {
            logger.info("Récupération de tous les signalements du citoyen avec l'id {}", citizenId);
            return reportingRepository.findAllByCitizenId(citizenId).stream()
                    .map(reportingMapper::toReportingDto)
                    .toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des signalements du citoyen avec l'id {}: {}", citizenId, e.getMessage());
            throw new RequestException("Erreur lors de la récupération des signalements du citoyen avec l'id " + citizenId, e.getStatus());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ReportingDto> getAllReportingsByMunicipality(Long municipalityId) {
        try {
            logger.info("Récupération de tous les signalements de la municipalité avec l'id {}", municipalityId);
            return reportingRepository.findAllByMunicipalityId(municipalityId).stream()
                    .map(reportingMapper::toReportingDto)
                    .toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des signalements de la municipalité avec l'id {}: {}", municipalityId, e.getMessage());
            throw new RequestException("Erreur lors de la récupération des signalements de la municipalité avec l'id " + municipalityId, e.getStatus());
        }
    }

    /**
     * @param wreckCategoryId
     * @return
     */
    @Override
    public List<ReportingDto> getAllByWreckCategoryId(Long wreckCategoryId) {
        try {
            logger.info("Récupération de tous les signalements de la catégorie de déchet avec l'id {}", wreckCategoryId);
            return reportingRepository.findAllByCategory_Id(wreckCategoryId).stream()
                    .map(reportingMapper::toReportingDto)
                    .toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des signalements de la catégorie de déchet avec l'id {}: {}", wreckCategoryId, e.getMessage());
            throw new RequestException("Erreur lors de la récupération des signalements de la catégorie de déchet avec l'id " + wreckCategoryId, e.getStatus());
        }
    }
}
