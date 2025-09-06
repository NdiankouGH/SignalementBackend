package com.signalement.service;

import com.signalement.dto.CitizenDto;

import java.util.List;

public interface ICitizenService {
    CitizenDto getCitizenById(Long id);

    CitizenDto createCitizen(CitizenDto citizenDto);

    CitizenDto updateCitizen(Long id, CitizenDto citizenDto);

    void deleteCitizen(Long id);

    List<CitizenDto> getAllCitizens();

    CitizenDto findByEmail(String email);
}
