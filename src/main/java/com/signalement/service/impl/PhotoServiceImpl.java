package com.signalement.service.impl;


import com.signalement.dao.PhotoRepository;
import com.signalement.dto.PhotoDto;
import com.signalement.entity.Photo;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.PhotoMapper;
import com.signalement.service.IPhotoService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Service
public class PhotoServiceImpl implements IPhotoService {
    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;
    private final MinioPhotoService minioPhotoService;
    private final MinioClient minioClient;
// ← injection


    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper, MinioPhotoService minioPhotoService, MinioClient minioClient) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.minioPhotoService = minioPhotoService;
        this.minioClient = minioClient;
    }

    private String generatePresignedUrl(String objectName) {
       try{ return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket("signalement")
                        .object(objectName)
                        .expiry((int) TimeUnit.HOURS.toSeconds(1))
                        .build()
        );
       }catch (Exception e) {
           throw new RuntimeException("Erreur lors de la génération de l'URL MinIO", e);
       }
    }
    @Override
    public List<PhotoDto> getAllPhotosByReporting(Long reportingId) {
        logger.info("Récupération des photos pour le signalement d'id : {}", reportingId);
        try {
            return photoRepository.findAllByReportingId(reportingId).stream()
                    .map(photo -> {
                        PhotoDto dto = photoMapper.toPhotoDto(photo);
                        dto.setUrl(generatePresignedUrl(photo.getUrl())); // génère URL temporaire
                        return dto;
                    }).toList();

        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des photos pour le signalement d'id : {}: {}", reportingId, e.getMessage());
            throw new RequestException("Erreur lors de la récupération des photos", e.getStatus());
        }
    }

    @Override
    public List<PhotoDto> getAllPhotos() {
        try {
            logger.info("Récupération de toutes les photos");
            return StreamSupport.stream(photoRepository.findAll().spliterator(), false)
                    .map(photo -> {
                        PhotoDto dto = photoMapper.toPhotoDto(photo);
                        dto.setUrl(generatePresignedUrl(photo.getUrl()));
                        return dto;
                    }).toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des photos: {}", e.getMessage());
            throw new RequestException("Erreur lors de la récupération des photos", e.getStatus());
        }
    }

    @Override
    public PhotoDto getPhotoById(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo non trouvée avec l'id: " + id));
        PhotoDto dto = photoMapper.toPhotoDto(photo);
        dto.setUrl(generatePresignedUrl(photo.getUrl()));
        return dto;
    }

    @Override
    public PhotoDto createPhoto(MultipartFile file, PhotoDto photoDto) {
        try {
            logger.info("Création d'une nouvelle photo pour le signalement : {}", photoDto.getReporting_id());
            String objectName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String url = minioPhotoService.uploadPhoto(file,objectName);
            photoDto.setUrl(url);

            return photoMapper.toPhotoDto(
                    photoRepository.save(
                            photoMapper.fromPhotoDto(photoDto)
                    )
            );
        } catch (RequestException e) {
            logger.error("Erreur lors de la création de la photo: {}", e.getMessage());
            throw new RequestException("Erreur lors de la création de la photo", e.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PhotoDto updatePhoto(Long id, PhotoDto photoDto) {
        return photoRepository.findById(id).map(
                photo -> {
                    photoDto.setId(photo.getId());
                    return photoMapper.toPhotoDto(
                            photoRepository.save(
                                    photoMapper.fromPhotoDto(photoDto)
                            )
                    );
                }
        ).orElseThrow(() -> new EntityNotFoundException("Photo non trouvée avec l'id: " + id));
    }

    @Override
    public void deletePhoto(Long id) {
        try {
            logger.info("Suppression de la photo avec l'id: {}", id);
            photoRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la suppression de la photo avec l'id: {}: {}", id, e.getMessage());
            throw new EntityNotFoundException("Erreur lors de la suppression de la photo avec l'id: " + id);
        }
    }
}
