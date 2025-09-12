package com.signalement.controller;

import com.signalement.dto.MunicipalityDto;
import com.signalement.service.impl.MunicipalityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    private final MunicipalityServiceImpl municipalityService;

    public MunicipalityController(MunicipalityServiceImpl municipalityService) {
        this.municipalityService = municipalityService;
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<MunicipalityDto>> getAllMunicipalities() {
        return ResponseEntity.ok(municipalityService.getAllMunicipalities());
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<MunicipalityDto> getMunicipalityById(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityService.getMunicipalityById(id));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<MunicipalityDto> createMunicipality(@RequestBody MunicipalityDto municipalityDto) {
        return ResponseEntity.ok(municipalityService.createMunicipality(municipalityDto));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<MunicipalityDto> updateMunicipality(@PathVariable Long id, @RequestBody MunicipalityDto municipalityDto) {
        return ResponseEntity.ok(municipalityService.updateMunicipality(id, municipalityDto));
    }

    @PreAuthorize("hasRole('MUNICIPAL_AGENT,ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMunicipality(@PathVariable Long id) {
        municipalityService.deleteMunicipality(id);
        return ResponseEntity.noContent().build();
    }
}
