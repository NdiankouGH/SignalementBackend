package com.signalement.service;

import com.signalement.dto.MunicipalAgentDto;

import java.util.List;

public interface IMunicipalityAgentService {

    List<MunicipalAgentDto> listAllMunicipalityAgents();

    MunicipalAgentDto getMunicipalityAgentById(Long id);

    MunicipalAgentDto createMunicipalityAgent(MunicipalAgentDto municipalAgentDto);

    MunicipalAgentDto updateMunicipalityAgent(Long id, MunicipalAgentDto municipalAgentDto);

    void deleteMunicipalityAgent(Long id);

}
