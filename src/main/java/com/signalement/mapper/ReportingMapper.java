package com.signalement.mapper;

import com.signalement.dto.ReportingDto;
import com.signalement.entity.Reporting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportingMapper {
    ReportingDto toReportingDto(Reporting reportingDto);

    Reporting fromReportingDto(ReportingDto reportingDto);

}
