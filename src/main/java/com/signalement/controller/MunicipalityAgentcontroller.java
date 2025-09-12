package com.signalement.controller;

import com.signalement.dto.MunicipalAgentDto;
import com.signalement.service.impl.MunicipalityAgentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalityAgents")
public class MunicipalityAgentcontroller {

    private final MunicipalityAgentServiceImpl municipalityAgentService;

    public MunicipalityAgentcontroller(MunicipalityAgentServiceImpl municipalityAgentService) {
        this.municipalityAgentService = municipalityAgentService;
    }

    @PreAuthorize("hasRole('ADMIN ,MUNICIPAL_AGENT')")
    @GetMapping("/all")
    public ResponseEntity<List<MunicipalAgentDto>> getAllIntervention() {
        return ResponseEntity.ok().body(municipalityAgentService.listAllMunicipalityAgents());
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MunicipalAgentDto> getMunicipalityAgentById(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityAgentService.getMunicipalityAgentById(id));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<MunicipalAgentDto> createMunicipalityAgent(@RequestBody MunicipalAgentDto municipalAgentDto) {
        return ResponseEntity.ok(municipalityAgentService.createMunicipalityAgent(municipalAgentDto));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<MunicipalAgentDto> updateMunicipalityAgent(@PathVariable Long id, @RequestBody MunicipalAgentDto municipalAgentDto) {
        return ResponseEntity.ok(municipalityAgentService.updateMunicipalityAgent(id, municipalAgentDto));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMunicipalityAgent(@PathVariable Long id) {
        municipalityAgentService.deleteMunicipalityAgent(id);
        return ResponseEntity.noContent().build();
    }
}
