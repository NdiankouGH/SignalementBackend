package com.signalement.service;


import com.signalement.dao.MunicipalAgentRepository;
import com.signalement.dto.MunicipalAgentDto;
import com.signalement.entity.MunicipalAgent;
import com.signalement.entity.Municipality;
import com.signalement.entity.Role;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.mapper.MunicipalityAgentMapper;
import com.signalement.service.impl.MunicipalityAgentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MunicipalAgentServiceTest {

    @Mock
    private MunicipalAgentRepository municipalAgentRepository;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private MunicipalityAgentServiceImpl municipalAgentService;
    @Mock
    private MunicipalityAgentMapper municipalAgentMapper;

    private MunicipalAgent municipalAgent;
    private MunicipalAgentDto municipalAgentDto;

    private Municipality municipality;


    @BeforeEach
    void setUp() {

        municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("Dakar Municipality");
        municipality.setRegion("Dakar ");
        municipality.setCode("DM001");
        municipality.setCountry("Senegal");


        municipalAgent = new MunicipalAgent();
        municipalAgent.setId(1L);
        municipalAgent.setFirstName("John");
        municipalAgent.setLastName("Doe");
        municipalAgent.setEmail("johndoe@gmail.com");
        municipalAgent.setMatricule("1234567890");
        municipalAgent.setFunction("Inspector");
        municipalAgent.setRole(Role.valueOf("MUNICIPAL_AGENT"));
        municipalAgent.setActive(true);
        municipalAgent.setMunicipality(municipality);

        municipalAgentDto = new MunicipalAgentDto();

        municipalAgentDto.setId(1L);
        municipalAgentDto.setFirstName("John");
        municipalAgentDto.setLastName("Doe");
        municipalAgentDto.setEmail("johndoe@gmail.com");
        municipalAgentDto.setMatricule("1234567890");
        municipalAgentDto.setFunction("Inspector");
        municipalAgentDto.setRole("ROLE_AGENT");
        municipalAgentDto.setActive(true);
        municipalAgentDto.setMunicipality_id(municipality.getId());
    }

    @Test
    void testCreateMunicipalAgent() {
        when(municipalAgentMapper.fromMunicipalAgentDto(municipalAgentDto)).thenReturn(municipalAgent);
        when(municipalAgentRepository.save(municipalAgent)).thenReturn(municipalAgent);
        when(municipalAgentMapper.toMunicipalAgentDto(municipalAgent)).thenReturn(municipalAgentDto);
        MunicipalAgentDto result = municipalAgentService.createMunicipalityAgent(municipalAgentDto);
        assert (result.getId().equals(municipalAgentDto.getId()));
        assertEquals(result.getFirstName(), municipalAgentDto.getFirstName());
        assertEquals(result.getLastName(), municipalAgentDto.getLastName());
        assertEquals(result.getEmail(), municipalAgentDto.getEmail());
        assertEquals(result.getMatricule(), municipalAgentDto.getMatricule());
        assertEquals(result.getFunction(), municipalAgentDto.getFunction());
        assertEquals(result.getRole(), municipalAgentDto.getRole());
    }

    @Test
    void testGetMunicipalAgentById() {
        when(municipalAgentRepository.findById(1L)).thenReturn(Optional.ofNullable(municipalAgent));
        when(municipalAgentMapper.toMunicipalAgentDto(municipalAgent)).thenReturn(municipalAgentDto);
        MunicipalAgentDto result = municipalAgentService.getMunicipalityAgentById(1L);
        assert (result.getId().equals(municipalAgentDto.getId()));
        assertEquals(result.getFirstName(), municipalAgentDto.getFirstName());
        assertEquals(result.getLastName(), municipalAgentDto.getLastName());
        assertEquals(result.getEmail(), municipalAgentDto.getEmail());
        assertEquals(result.getMatricule(), municipalAgentDto.getMatricule());
        assertEquals(result.getFunction(), municipalAgentDto.getFunction());
        assertEquals(result.getRole(), municipalAgentDto.getRole());
    }

    @Test
    void testGetMunicipalAgentById_NotFound() {
        when(municipalAgentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> municipalAgentService.getMunicipalityAgentById(1L));
        assertEquals("Agent municipal non trouvé avec l'id: 1", assertThrows(EntityNotFoundException.class, () -> municipalAgentService.getMunicipalityAgentById(1L)).getMessage());
    }

    @Test
    void testUpdateMunicipalAgent() {
        when(municipalAgentRepository.findById(1L)).thenReturn(Optional.ofNullable(municipalAgent));
        when(municipalAgentMapper.toMunicipalAgentDto(municipalAgent)).thenReturn(municipalAgentDto);
        when(municipalAgentMapper.fromMunicipalAgentDto(municipalAgentDto)).thenReturn(municipalAgent);
        when(municipalAgentRepository.save(municipalAgent)).thenReturn(municipalAgent);
        MunicipalAgentDto result = municipalAgentService.updateMunicipalityAgent(1L, municipalAgentDto);
        assert (result.getId().equals(municipalAgentDto.getId()));
        assertEquals(result.getMunicipality_id(), municipalAgentDto.getMunicipality_id());
        assertEquals(result.getFirstName(), municipalAgentDto.getFirstName());
        assertEquals(result.getLastName(), municipalAgentDto.getLastName());
        assertEquals(result.getEmail(), municipalAgentDto.getEmail());
        assertEquals(result.getMatricule(), municipalAgentDto.getMatricule());
        assertEquals(result.getFunction(), municipalAgentDto.getFunction());
        assertEquals(result.getRole(), municipalAgentDto.getRole());
        verify(municipalAgentRepository).save(municipalAgent);
    }

    @Test
    void testUpdateMunicipalAgent_NotFound() {
        when(municipalAgentRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> municipalAgentService.updateMunicipalityAgent(1L, municipalAgentDto));
        assertEquals("Agent municipal non trouvé avec l'id: 1", assertThrows(EntityNotFoundException.class, () -> municipalAgentService.updateMunicipalityAgent(1L, municipalAgentDto)).getMessage());
    }

    @Test
    void testDeleteMunicipalAgent() {
        municipalAgentService.deleteMunicipalityAgent(1L);
        verify(municipalAgentRepository).deleteById(1L);
    }

    @Test
    void testDeleteMunicipalAgent_NotFound() {
        doThrow(new EntityNotFoundException()).when(municipalAgentRepository).deleteById(1L);
        assertEquals("Erreur lors de la suppression de l'agent municipal avec l'id: " + 1L, assertThrows(EntityNotFoundException.class, () -> municipalAgentService.deleteMunicipalityAgent(1L)).getMessage());
    }

    @Test
    void testGetAllMunicipalAgents() {
        List<MunicipalAgent> municipalAgentDtoList = List.of(municipalAgent);
        when(municipalAgentMapper.toMunicipalAgentDto(municipalAgent)).thenReturn(municipalAgentDto);
        when(municipalAgentRepository.findAll()).thenReturn(municipalAgentDtoList);
        List<MunicipalAgentDto> result = municipalAgentService.listAllMunicipalityAgents();
        assert (result.size() == 1);
        assert (result.get(0).getId().equals(municipalAgentDto.getId()));
        assertEquals(result.get(0).getFirstName(), municipalAgentDto.getFirstName());
        assertEquals(result.get(0).getLastName(), municipalAgentDto.getLastName());
        assertEquals(result.get(0).getEmail(), municipalAgentDto.getEmail());
        assertEquals(result.get(0).getMatricule(), municipalAgentDto.getMatricule());
        assertEquals(result.get(0).getFunction(), municipalAgentDto.getFunction());
        verify(municipalAgentRepository).findAll();
    }

}