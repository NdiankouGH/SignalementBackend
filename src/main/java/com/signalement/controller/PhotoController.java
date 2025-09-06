package com.signalement.controller;

import com.signalement.dto.PhotoDto;
import com.signalement.service.impl.PhotoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoServiceImpl photoService;

    public PhotoController(PhotoServiceImpl photoService) {
        this.photoService = photoService;
    }

    @GetMapping("all")
    public ResponseEntity<List<PhotoDto>> getAllPhotos() {
        return ResponseEntity.ok(photoService.getAllPhotos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDto> getPhotoById(@PathVariable Long id) {
        return ResponseEntity.ok(photoService.getPhotoById(id));
    }

    @GetMapping("/reporting/{reportingId}")
    public ResponseEntity<List<PhotoDto>> getPhotoByReporting(@PathVariable Long reportingId) {
        return ResponseEntity.ok(photoService.getAllPhotosByReporting(reportingId));
    }

    @PostMapping("/create")
    public ResponseEntity<PhotoDto> createPhoto(@RequestBody PhotoDto photoDto) {
        return ResponseEntity.ok(photoService.createPhoto(photoDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PhotoDto> updatePhoto(@PathVariable Long id, @RequestBody
    PhotoDto photoDto) {
        return ResponseEntity.ok(photoService.updatePhoto(id, photoDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.noContent().build();
    }
}
