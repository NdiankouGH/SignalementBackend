package com.signalement.mapper;

import com.signalement.dto.MunicipalAgentDto;
import com.signalement.entity.MunicipalAgent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface MunicipalityAgentMapper {
    MunicipalAgentDto toMunicipalAgentDto(MunicipalAgent municipalAgent);

    MunicipalAgent fromMunicipalAgentDto(MunicipalAgentDto municipalAgentDto);
}
