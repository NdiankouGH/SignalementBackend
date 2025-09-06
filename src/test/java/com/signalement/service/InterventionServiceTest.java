package com.signalement.service;


import com.signalement.dao.InterventionRepository;
import com.signalement.dto.InterventionDto;
import com.signalement.entity.Intervention;
import com.signalement.entity.Municipality;
import com.signalement.entity.StatusIntervention;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.mapper.InterventionMapper;
import com.signalement.service.impl.InterventionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterventionServiceTest {

    @Mock
    private InterventionRepository interventionRepository;
    @Mock
    private InterventionMapper interventionMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private InterventionServiceImpl interventionService;

    private InterventionDto interventionDto;
    private Intervention intervention;

    private Municipality municipality;
    @BeforeEach
    void setUp() {
        municipality = new Municipality();
        municipality.setId(1L);
        municipality.setName("Dakar Municipality");
        municipality.setRegion("Dakar ");
        municipality.setCode("DM001");
        municipality.setCountry("Senegal");


        interventionDto = new InterventionDto();
        interventionDto.setId(1L);
        interventionDto.setInterventionDate(new Date());
        interventionDto.setComment("intervention comment");
        interventionDto.setStatus("PENDING");
        interventionDto.setMunicipality_id(municipality.getId());

        intervention = new Intervention();
        intervention.setId(1L);
        intervention.setInterventionDate(new Date());
        intervention.setComment("intervention comment");
        intervention.setStatus(StatusIntervention.valueOf("PENDING"));
        intervention.setMunicipality(municipality);







    }
    @Test
    void testCreateIntervention() {
        when(interventionMapper.toInterventionDto(intervention)).thenReturn(interventionDto);
        when(interventionMapper.fromInterventionDto(interventionDto)).thenReturn(intervention);
        when(interventionRepository.save(Mockito.any(Intervention.class))).thenReturn(intervention);

        InterventionDto result = interventionService.createIntervention(interventionDto);
        assert(result.getId().equals(interventionDto.getId()));
        assert(result.getComment().equals(interventionDto.getComment()));
        assert(result.getStatus().equals(interventionDto.getStatus()));
        assert(result.getMunicipality_id().equals(interventionDto.getMunicipality_id()));
        verify(interventionRepository).save(any(Intervention.class));
    }

    @Test
    void  testGetInterventionById() {
        when(interventionMapper.toInterventionDto(intervention)).thenReturn(interventionDto);
        when(interventionRepository.findById(1L)).thenReturn(Optional.of(intervention));

        InterventionDto result = interventionService.getInterventionById(1L);
        assertNotNull(result);
        assertEquals(result.getId(),interventionDto.getId());
        assertEquals(result.getComment(),interventionDto.getComment());
        assertEquals(result.getStatus(),interventionDto.getStatus());
        assertEquals(result.getMunicipality_id(),interventionDto.getMunicipality_id());
        verify(interventionRepository).findById(1L);
    }

    @Test
    void  testGetInterventionByIdNotFound() {
        when(interventionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> interventionService.getInterventionById(1L));
    }

    @Test
    void testUpdateIntervention() {
        when(interventionMapper.toInterventionDto(intervention)).thenReturn(interventionDto);
        when(interventionMapper.fromInterventionDto(interventionDto)).thenReturn(intervention);
        when(interventionRepository.findById(1L)).thenReturn(Optional.of(intervention));
        when(interventionRepository.save(Mockito.any(Intervention.class))).thenReturn(intervention);

        InterventionDto result = interventionService.updateIntervention(1L,interventionDto);
        assertNotNull(result);
        assertEquals(result.getId(),interventionDto.getId());
        assertEquals(result.getComment(),interventionDto.getComment());
        assertEquals(result.getStatus(),interventionDto.getStatus());
        assertEquals(result.getMunicipality_id(),interventionDto.getMunicipality_id());
        verify(interventionRepository).findById(1L);
        verify(interventionRepository).save(any(Intervention.class));
    }

    @Test
    void  testUpdateInterventionNotFound() {
        when(interventionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> interventionService.updateIntervention(1L,interventionDto));
    }

    @Test
    void testDeleteIntervention() {
        doNothing().when(interventionRepository).deleteById(1L);
        interventionService.deleteIntervention(1L);
        verify(interventionRepository).deleteById(1L);
    }
    @Test
    void  testDeleteInterventionNotFound() {
        doThrow(new EntityNotFoundException ()).when(interventionRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> interventionService.deleteIntervention(1L));
    }

    @Test
    void testGetAllInterventions(){
        List<Intervention> interventionList = List.of(intervention);
        when(interventionRepository.findAll()).thenReturn(interventionList);
        when(interventionMapper.toInterventionDto(intervention)).thenReturn(interventionDto);

        List<InterventionDto> result = interventionService.getAllInterventions();
        assertNotNull(result);
        assertEquals(1,result.size());
        verify(interventionRepository).findAll();
    }
}
