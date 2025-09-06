package com.signalement.controller;

import com.signalement.dto.InterventionDto;
import com.signalement.service.impl.InterventionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interventions")
public class Interventioncontroller {
    private final InterventionServiceImpl interventionService;

    public Interventioncontroller(InterventionServiceImpl interventionService) {
        this.interventionService = interventionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<InterventionDto>> getAllInterventions() {
        return ResponseEntity.ok().body(interventionService.getAllInterventions());
    }

    @PostMapping("/create")
    public ResponseEntity<InterventionDto> createIntervention(InterventionDto interventionDto) {
        return ResponseEntity.ok(interventionService.createIntervention(interventionDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionDto> getInterventionById(Long id) {
        return ResponseEntity.ok(interventionService.getInterventionById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<InterventionDto> updateIntervention(@PathVariable Long id, @RequestBody InterventionDto interventionDto) {
        return ResponseEntity.ok(interventionService.updateIntervention(id, interventionDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIntervention(@PathVariable Long id) {
        interventionService.deleteIntervention(id);
        return ResponseEntity.noContent().build();
    }
}
