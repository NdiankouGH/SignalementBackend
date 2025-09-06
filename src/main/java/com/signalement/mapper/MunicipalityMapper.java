package com.signalement.mapper;

import com.signalement.dto.MunicipalityDto;
import com.signalement.entity.Municipality;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MunicipalityMapper {
    MunicipalityDto toMunicipalityDto(Municipality municipality);

    Municipality fromMunicipalityDto(MunicipalityDto municipalityDto);
}
