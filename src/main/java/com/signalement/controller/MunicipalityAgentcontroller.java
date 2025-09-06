package com.signalement.controller;

import com.signalement.dto.MunicipalAgentDto;
import com.signalement.service.impl.MunicipalityAgentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalityAgents")
public class MunicipalityAgentcontroller {

    private final MunicipalityAgentServiceImpl municipalityAgentService;

    public MunicipalityAgentcontroller(MunicipalityAgentServiceImpl municipalityAgentService) {
        this.municipalityAgentService = municipalityAgentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MunicipalAgentDto>> getAllIntervention() {
        return ResponseEntity.ok().body(municipalityAgentService.listAllMunicipalityAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalAgentDto> getMunicipalityAgentById(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityAgentService.getMunicipalityAgentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MunicipalAgentDto> createMunicipalityAgent(@RequestBody MunicipalAgentDto municipalAgentDto) {
        return ResponseEntity.ok(municipalityAgentService.createMunicipalityAgent(municipalAgentDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MunicipalAgentDto> updateMunicipalityAgent(@PathVariable Long id, @RequestBody MunicipalAgentDto municipalAgentDto) {
        return ResponseEntity.ok(municipalityAgentService.updateMunicipalityAgent(id, municipalAgentDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMunicipalityAgent(@PathVariable Long id) {
        municipalityAgentService.deleteMunicipalityAgent(id);
        return ResponseEntity.noContent().build();
    }
}
