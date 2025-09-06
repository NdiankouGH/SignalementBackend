package com.signalement.controller;

import com.signalement.dto.MunicipalityDto;
import com.signalement.service.impl.MunicipalityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    private final MunicipalityServiceImpl municipalityService;

    public MunicipalityController(MunicipalityServiceImpl municipalityService) {
        this.municipalityService = municipalityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MunicipalityDto>> getAllMunicipalities() {
        return ResponseEntity.ok(municipalityService.getAllMunicipalities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MunicipalityDto> getMunicipalityById(@PathVariable Long id) {
        return ResponseEntity.ok(municipalityService.getMunicipalityById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<MunicipalityDto> createMunicipality(@RequestBody MunicipalityDto municipalityDto) {
        return ResponseEntity.ok(municipalityService.createMunicipality(municipalityDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MunicipalityDto> updateMunicipality(@PathVariable Long id, @RequestBody MunicipalityDto municipalityDto) {
        return ResponseEntity.ok(municipalityService.updateMunicipality(id, municipalityDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMunicipality(@PathVariable Long id) {
        municipalityService.deleteMunicipality(id);
        return ResponseEntity.noContent().build();
    }
}
