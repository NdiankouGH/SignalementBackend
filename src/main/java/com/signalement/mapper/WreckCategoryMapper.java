package com.signalement.mapper;

import com.signalement.dto.WreckCategoryDto;
import com.signalement.entity.WreckCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WreckCategoryMapper {
    WreckCategoryDto toWreckCategoryDto(WreckCategory wreckCategory);

    WreckCategory fromWreckCategoryDto(WreckCategoryDto wreckCategoryDto);
}
