package com.signalement.service;

import com.signalement.dto.InterventionDto;

import java.util.List;

public interface IInterventionService {
    // Define methods related to Intervention entity
    InterventionDto createIntervention(InterventionDto interventionDto);

    InterventionDto getInterventionById(Long id);

    InterventionDto updateIntervention(Long id, InterventionDto interventionDto);

    void deleteIntervention(Long id);

    List<InterventionDto> getAllInterventions();

    InterventionDto getInterventionByReportingId(Long reportingId);
}
