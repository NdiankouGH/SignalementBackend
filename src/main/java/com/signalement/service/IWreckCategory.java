package com.signalement.service;

import com.signalement.dto.WreckCategoryDto;

import java.util.List;

public interface IWreckCategory {
    WreckCategoryDto getWreckCategoryDto(Long id);

    WreckCategoryDto createWreckCategoryDto(WreckCategoryDto wreckCategoryDto);

    WreckCategoryDto updateWreckCategoryDto(Long id, WreckCategoryDto wreckCategoryDto);

    void deleteWreckCategoryDto(Long id);

    List<WreckCategoryDto> getAllWreckCategories();
}
