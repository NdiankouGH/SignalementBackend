package com.signalement.controller;

import com.signalement.dto.WreckCategoryDto;
import com.signalement.service.impl.WreckCategoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wreck-categories")
public class WreckCategoryController {

    private final WreckCategoryImpl wreckCategoryImpl;

    public WreckCategoryController(WreckCategoryImpl wreckCategoryImpl) {
        this.wreckCategoryImpl = wreckCategoryImpl;
    }

    @GetMapping("all")
    public ResponseEntity<List<WreckCategoryDto>> getWreckCategories() {
        return ResponseEntity.ok(wreckCategoryImpl.getAllWreckCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WreckCategoryDto> getWreckCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(wreckCategoryImpl.getWreckCategoryDto(id));

    }

    @PostMapping("/create")
    public ResponseEntity<WreckCategoryDto> createWreckCategory(@RequestBody WreckCategoryDto wreckCategoryDto) {
        return ResponseEntity.ok(wreckCategoryImpl.createWreckCategoryDto(wreckCategoryDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WreckCategoryDto> updateWreckCategory(@PathVariable Long id, @RequestBody WreckCategoryDto wreckCategoryDto) {
        return ResponseEntity.ok(wreckCategoryImpl.updateWreckCategoryDto(id, wreckCategoryDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteWreckCategory(@PathVariable Long id) {
        wreckCategoryImpl.deleteWreckCategoryDto(id);
        return ResponseEntity.noContent().build();
    }
}
