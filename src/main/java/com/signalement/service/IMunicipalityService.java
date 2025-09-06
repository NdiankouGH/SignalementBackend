package com.signalement.service;

import com.signalement.dto.MunicipalityDto;

import java.util.List;

public interface IMunicipalityService {

    List<MunicipalityDto> getAllMunicipalities();

    MunicipalityDto getMunicipalityById(Long id);

    MunicipalityDto createMunicipality(MunicipalityDto municipalityDto);

    MunicipalityDto updateMunicipality(Long id, MunicipalityDto municipalityDto);

    void deleteMunicipality(Long id);
}
