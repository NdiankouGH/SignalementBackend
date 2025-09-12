package com.signalement.controller;

import com.signalement.dto.ReportingDto;
import com.signalement.service.impl.ReportingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportings")
public class ReportingController {

    private final ReportingServiceImpl reportingService;

    public ReportingController(ReportingServiceImpl reportingService) {
        this.reportingService = reportingService;
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @GetMapping("all")
    public ResponseEntity<List<ReportingDto>> getAllReportings() {
        return ResponseEntity.ok(reportingService.getAllReportings());
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ReportingDto> getReportingById(@PathVariable Long id) {
        return ResponseEntity.ok(reportingService.getReportingById(id));
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @GetMapping("citizen/{citizenId}")
    public ResponseEntity<List<ReportingDto>> getReportingsByCitizenId(@PathVariable Long citizenId) {
        return ResponseEntity.ok(reportingService.getAllReportingsByCitizenId(citizenId));
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @GetMapping("municipality/{municipalityId}")
    public ResponseEntity<List<ReportingDto>> getReportingsByMunicipalityId(@PathVariable Long municipalityId) {
        return ResponseEntity.ok(reportingService.getAllReportingsByMunicipality(municipalityId));
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ReportingDto> createReporting(@RequestBody ReportingDto reportingDto) {
        return ResponseEntity.ok(reportingService.createReporting(reportingDto));
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ReportingDto> updateReporting(@PathVariable Long id, @RequestBody ReportingDto reportingDto) {
        return ResponseEntity.ok(reportingService.updateReporting(id, reportingDto));
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReporting(@PathVariable Long id) {
        reportingService.deleteReporting(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CITIZEN,ADMIN')")
    @GetMapping("wreck/{id}")
    public ResponseEntity<List<ReportingDto>> getReportingsByWreckId(@PathVariable Long id) {
        return ResponseEntity.ok(reportingService.getAllByWreckCategoryId(id));
    }
}
