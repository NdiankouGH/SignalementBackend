package com.signalement.mapper;

import com.signalement.dto.InterventionDto;
import com.signalement.entity.Intervention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface InterventionMapper {
    InterventionDto toInterventionDto(Intervention intervention);

    Intervention fromInterventionDto(InterventionDto interventionDto);

}
