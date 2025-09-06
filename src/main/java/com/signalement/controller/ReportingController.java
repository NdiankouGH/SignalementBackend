package com.signalement.controller;

import com.signalement.dto.ReportingDto;
import com.signalement.service.impl.ReportingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportings")
public class ReportingController {

    private final ReportingServiceImpl reportingService;

    public ReportingController(ReportingServiceImpl reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ReportingDto>> getAllReportings() {
        return ResponseEntity.ok(reportingService.getAllReportings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportingDto> getReportingById(@PathVariable Long id) {
        return ResponseEntity.ok(reportingService.getReportingById(id));
    }

    @GetMapping("citizen/{citizenId}")
    public ResponseEntity<List<ReportingDto>> getReportingsByCitizenId(@PathVariable Long citizenId) {
        return ResponseEntity.ok(reportingService.getAllReportingsByCitizenId(citizenId));
    }

    @GetMapping("municipality/{municipalityId}")
    public ResponseEntity<List<ReportingDto>> getReportingsByMunicipalityId(@PathVariable Long municipalityId) {
        return ResponseEntity.ok(reportingService.getAllReportingsByMunicipality(municipalityId));
    }

    @PostMapping("/create")
    public ResponseEntity<ReportingDto> createReporting(@RequestBody ReportingDto reportingDto) {
        return ResponseEntity.ok(reportingService.createReporting(reportingDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReportingDto> updateReporting(@PathVariable Long id, @RequestBody ReportingDto reportingDto) {
        return ResponseEntity.ok(reportingService.updateReporting(id, reportingDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReporting(@PathVariable Long id) {
        reportingService.deleteReporting(id);
        return ResponseEntity.noContent().build();
    }
}
