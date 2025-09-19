package com.signalement.service;

import com.signalement.dto.PhotoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPhotoService {
    List<PhotoDto> getAllPhotosByReporting(Long reportingId);

    List<PhotoDto> getAllPhotos();

    PhotoDto getPhotoById(Long id);


    PhotoDto createPhoto(MultipartFile file, PhotoDto photoDto);

    PhotoDto updatePhoto(Long id, PhotoDto photoDto);

    void deletePhoto(Long id);


}
