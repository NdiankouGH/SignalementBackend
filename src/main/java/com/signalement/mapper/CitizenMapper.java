package com.signalement.mapper;

import com.signalement.dto.CitizenDto;
import com.signalement.entity.Citizen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitizenMapper {
    CitizenDto toCitizenDto(Citizen citizen);

    Citizen fromCitizenDto(CitizenDto citizenDto);
}
