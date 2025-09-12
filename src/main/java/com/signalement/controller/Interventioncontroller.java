package com.signalement.controller;

import com.signalement.dto.InterventionDto;
import com.signalement.service.impl.InterventionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interventions")
public class Interventioncontroller {
    private final InterventionServiceImpl interventionService;

    public Interventioncontroller(InterventionServiceImpl interventionService) {
        this.interventionService = interventionService;
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<InterventionDto>> getAllInterventions() {
        return ResponseEntity.ok().body(interventionService.getAllInterventions());
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<InterventionDto> createIntervention(InterventionDto interventionDto) {
        return ResponseEntity.ok(interventionService.createIntervention(interventionDto));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<InterventionDto> getInterventionById(@PathVariable Long id) {
        return ResponseEntity.ok(interventionService.getInterventionById(id));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<InterventionDto> updateIntervention(@PathVariable Long id, @RequestBody InterventionDto interventionDto) {
        return ResponseEntity.ok(interventionService.updateIntervention(id, interventionDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIntervention(@PathVariable Long id) {
        interventionService.deleteIntervention(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/reporting/{reportingId}")
    public ResponseEntity<InterventionDto> getInterventionsByReportingId(@PathVariable Long reportingId) {
        InterventionDto interventions = interventionService.getInterventionByReportingId(reportingId);
        return ResponseEntity.ok(interventions);
    }
}
