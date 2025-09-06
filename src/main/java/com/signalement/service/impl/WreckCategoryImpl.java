package com.signalement.service.impl;

import com.signalement.dao.WreckCategoryRepository;
import com.signalement.dto.WreckCategoryDto;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.WreckCategoryMapper;
import com.signalement.service.IWreckCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WreckCategoryImpl implements IWreckCategory {

    private static final Logger logger = LoggerFactory.getLogger(WreckCategoryImpl.class);
    private final WreckCategoryRepository wreckCategoryRepository;
    private final WreckCategoryMapper wreckCategoryMapper;

    public WreckCategoryImpl(WreckCategoryRepository wreckCategoryRepository, WreckCategoryMapper wreckCategoryMapper) {
        this.wreckCategoryRepository = wreckCategoryRepository;
        this.wreckCategoryMapper = wreckCategoryMapper;
    }


    @Override
    public WreckCategoryDto getWreckCategoryDto(Long id) {
        logger.info("Recupération d'une catégorie d'épave avec l'id: {}", id);
        return wreckCategoryMapper.toWreckCategoryDto(
                wreckCategoryRepository.findById(id).orElseThrow(
                        () -> new EntityNotFoundException("Catégorie d'épave non trouvée")
                )
        );
    }

    @Override
    public WreckCategoryDto createWreckCategoryDto(WreckCategoryDto wreckCategoryDto) {
        try {
            logger.info("Création d'une nouvelle catégorie d'épave");
            return wreckCategoryMapper.toWreckCategoryDto(
                    wreckCategoryRepository.save(
                            wreckCategoryMapper.fromWreckCategoryDto(wreckCategoryDto)
                    )
            );
        } catch (RequestException e) {
            throw new RequestException("Erreur lors de la création de la catégorie d'épave", e.getStatus());
        }
    }

    @Override
    public WreckCategoryDto updateWreckCategoryDto(Long id, WreckCategoryDto wreckCategoryDto) {
        return wreckCategoryRepository.findById(id).map(
                wreckCategory -> {
                    wreckCategory.setId(wreckCategory.getId());
                    return wreckCategoryMapper.toWreckCategoryDto(
                            wreckCategoryRepository.save(
                                    wreckCategoryMapper.fromWreckCategoryDto(wreckCategoryDto)
                            )
                    );
                }
        ).orElseThrow(
                () -> new EntityNotFoundException("Catégorie d'épave non trouvée avec l'id: " + id)
        );
    }

    @Transactional
    @Override
    public void deleteWreckCategoryDto(Long id) {
        try {
            logger.info("Suppression de la catégorie d'épave avec l'id: {}", id);
            wreckCategoryRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            logger.error("Erreur lors de la suppression de la catégorie d'épave avec l'id: {}: {}", id, e.getMessage());
            throw new EntityNotFoundException("Erreur lors de la suppression de la catégorie d'épave avec l'id: " + id);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<WreckCategoryDto> getAllWreckCategories() {
        logger.info("Récupération de toutes les catégories d'épave");
        return wreckCategoryRepository.findAll().stream().map(
                wreckCategoryMapper::toWreckCategoryDto
        ).toList();
    }
}
