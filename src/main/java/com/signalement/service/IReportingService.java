package com.signalement.service;

import com.signalement.dto.ReportingDto;

import java.util.List;

public interface IReportingService {
    ReportingDto createReporting(ReportingDto reportingDto);

    ReportingDto getReportingById(Long id);

    void deleteReporting(Long id);

    ReportingDto updateReporting(Long id, ReportingDto reportingDto);

    List<ReportingDto> getAllReportings();

    List<ReportingDto> getAllReportingsByCitizenId(Long citizenId);

    List<ReportingDto> getAllReportingsByMunicipality(Long municipalityId);
    List<ReportingDto> getAllByWreckCategoryId(Long wreckCategoryId);
}
