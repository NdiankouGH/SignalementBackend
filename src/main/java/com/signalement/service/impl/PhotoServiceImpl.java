package com.signalement.service.impl;


import com.signalement.dao.PhotoRepository;
import com.signalement.dto.PhotoDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.PhotoMapper;
import com.signalement.service.IPhotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class PhotoServiceImpl implements IPhotoService {
    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;

    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    @Override
    public List<PhotoDto> getAllPhotosByReporting(Long reportingId) {
        logger.info("Récupération des photos pour le signalement d'id : {}", reportingId);
        try {
            return StreamSupport.stream(photoRepository.findAllByReportingId(reportingId).spliterator(), false)
                    .map(photoMapper::toPhotoDto).toList();

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
                    .map(photoMapper::toPhotoDto).toList();
        } catch (RequestException e) {
            logger.error("Erreur lors de la récupération des photos: {}", e.getMessage());
            throw new RequestException("Erreur lors de la récupération des photos", e.getStatus());
        }
    }

    @Override
    public PhotoDto getPhotoById(Long id) {
        return photoMapper.toPhotoDto(photoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Photo non trouvée avec l'id: " + id)));
    }

    @Override
    public PhotoDto createPhoto(PhotoDto photoDto) {
        try {
            logger.info("Création d'une nouvelle photo");
            return photoMapper.toPhotoDto(
                    photoRepository.save(
                            photoMapper.fromPhotoDto(photoDto)
                    )
            );
        } catch (RequestException e) {
            logger.error("Erreur lors de la création de la photo: {}", e.getMessage());
            throw new RequestException("Erreur lors de la création de la photo", e.getStatus());
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
