package com.signalement.controller;

import com.signalement.dto.CitizenDto;
import com.signalement.service.impl.CitizenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {
    private static final Logger logger = LoggerFactory.getLogger(CitizenController.class);
    private CitizenServiceImpl citizenService;

    public CitizenController(CitizenServiceImpl citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenDto> getCitizenById(Long id) {
        logger.info("Reponse pour la recherche d'un citoyen par son id");
        return ResponseEntity.ok(citizenService.getCitizenById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CitizenDto> createCitizen(@RequestBody CitizenDto citizenDto) {
        logger.info("Reponse pour la création d'un citoyen");
        return ResponseEntity.ok(citizenService.createCitizen(citizenDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CitizenDto> updateCitizen(@PathVariable Long id, @RequestBody CitizenDto citizenDto) {
        logger.info(" modification d'un citoyen");
        return ResponseEntity.ok(citizenService.updateCitizen(id, citizenDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCitizen(@PathVariable Long id) {
        logger.info(" suppression d'un citoyen");
        citizenService.deleteCitizen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CitizenDto>> getAllCitizen() {
        return ResponseEntity.ok().body(citizenService.getAllCitizens());
    }

}
