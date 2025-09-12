package com.signalement.service;

import com.signalement.dao.CitizenRepository;
import com.signalement.dto.CitizenDto;
import com.signalement.entity.Citizen;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.mapper.CitizenMapper;
import com.signalement.service.impl.CitizenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CitizenServiceTest {

    @InjectMocks
    private CitizenServiceImpl citizenService;
    @Mock
    private CitizenRepository citizenRepository;
    @Mock
    private CitizenMapper citizenMapper;

    @Mock
    private MessageSource messageSource;

    private CitizenDto citizen;
    private Citizen citizenEntity;

    @BeforeEach
    void setUp() {
        citizen = new CitizenDto();
        citizen.setId(1L);
        citizen.setFirstName("Papis");
        citizen.setLastName("Ndoye");
        citizen.setEmail("papisndoye59@gmail.com");
        citizen.setAddress("Dakar Hlm patte d'oie");
        citizen.setPhoneNumber("770931244");
        citizen.setCity("Dakar");
        citizen.setPassword("passer1234");

        citizenEntity = new Citizen();
        citizen.setId(1L);
        citizen.setFirstName("Papis");
        citizen.setLastName("Ndoye");
        citizen.setEmail("papisndoye59@gmail.com");
        citizen.setAddress("Dakar Hlm patte d'oie");
        citizen.setPhoneNumber("770931244");
        citizen.setCity("Dakar");
        citizen.setPassword("passer1234");

    }

    @Test
    void testCreateCitizenSuccess() {
        when(citizenMapper.fromCitizenDto(citizen)).thenReturn(citizenEntity);
        when(citizenRepository.save(any(Citizen.class))).thenReturn(citizenEntity);
        when(citizenMapper.toCitizenDto(citizenEntity)).thenReturn(citizen);

        CitizenDto createdCitizen = citizenService.createCitizen(citizen);
        assertEquals("Papis", createdCitizen.getFirstName());
        assertEquals("Ndoye", createdCitizen.getLastName());
        assertEquals("papisndoye59@gmail.com", createdCitizen.getEmail());
        assertEquals("Dakar Hlm patte d'oie", createdCitizen.getAddress());
        assertEquals("770931244", createdCitizen.getPhoneNumber());
        verify(citizenRepository).save(any(Citizen.class));
    }

    @Test
    void testGetCitizenByIdSuccess() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.of(citizenEntity));
        when(citizenMapper.toCitizenDto(citizenEntity)).thenReturn(citizen);

        CitizenDto foundCitizen = citizenService.getCitizenById(1L);
        assertEquals(1L, foundCitizen.getId());
        assertEquals("Papis", foundCitizen.getFirstName());
        verify(citizenRepository).findById(1L);
    }


    @Test
    void testGetCitizenByIdFailure() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> citizenService.getCitizenById(1L));
    }

    @Test
    void testUpdateCitizenSuccess() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.of(citizenEntity));
        when(citizenMapper.toCitizenDto(citizenEntity)).thenReturn(citizen);
        when(citizenMapper.fromCitizenDto(citizen)).thenReturn(citizenEntity);
        when(citizenRepository.save(any(Citizen.class))).thenReturn(citizenEntity);

        CitizenDto updatedCitizen = citizenService.updateCitizen(1L, citizen);
        assertEquals("Papis", updatedCitizen.getFirstName());
        assertEquals("Ndoye", updatedCitizen.getLastName());
        verify(citizenRepository).findById(1L);
        verify(citizenRepository).save(any(Citizen.class));
    }

    @Test
    void testUpdateCitizenFailure() {
        when(citizenRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> citizenService.updateCitizen(1L, citizen));
    }

    @Test
    void testDeleteCitizenSuccess() {
        doNothing().when(citizenRepository).deleteById(1L);
        citizenService.deleteCitizen(1L);
        verify(citizenRepository).deleteById(1L);
    }

    @Test
    void testDeleteCitizenFailure() {
        doThrow(new EntityNotFoundException()).when(citizenRepository).deleteById(1L);
        assertThrows(EntityNotFoundException.class, () -> citizenService.deleteCitizen(1L));
    }


    @Test
    void testGetAllCitizens() {
        List<Citizen> citizenList = List.of(citizenEntity);
        when(citizenRepository.findAll()).thenReturn(citizenList);
        when(citizenMapper.toCitizenDto(citizenEntity)).thenReturn(citizen);

        List<CitizenDto> allCitizen = citizenService.getAllCitizens();
        assertEquals(1, allCitizen.size());
        assertEquals("Papis", allCitizen.get(0).getFirstName());
        verify(citizenRepository).findAll();
    }

    @Test
    void testFindByEmailSuccess() {
        when(citizenRepository.findByEmail("papisndoye59@gmail.com")).thenReturn(Optional.of(citizenEntity));
        when(citizenMapper.toCitizenDto(citizenEntity)).thenReturn(citizen);
        CitizenDto foundCitizen = citizenService.findByEmail("papisndoye59@gmail.com");

        assertNotNull(foundCitizen);
        assertEquals("Papis", foundCitizen.getFirstName());
        verify(citizenRepository).findByEmail("papisndoye59@gmail.com");
    }

    @Test
    void testFindByEmailFailure() {
        when(citizenRepository.findByEmail("papisndoye59@gmail.com")).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> citizenService.findByEmail("papisndoye59@gmail.com"));
    }
}
