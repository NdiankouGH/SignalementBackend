package com.signalement.service;

import com.signalement.dao.ReportingRepository;
import com.signalement.dto.ReportingDto;
import com.signalement.entity.Reporting;
import com.signalement.entity.StatusSignalement;
import com.signalement.exception.EntityNotFoundException;
import com.signalement.exception.RequestException;
import com.signalement.mapper.ReportingMapper;
import com.signalement.service.impl.ReportingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportingServiceTest {

    @InjectMocks
    private ReportingServiceImpl reportingService;

    @Mock
    private ReportingRepository reportingRepository;
    @Mock
    private MessageSource messageSource;
    @Mock
    private ReportingMapper reportingMapper;

    private Reporting reporting;
    private ReportingDto reportingDto;

    @BeforeEach
     void setup() {

        reporting = new Reporting();
        reporting.setId(1L);
        reporting.setTitle("Pothole sur Main St");
        reporting.setDescription("Il y a un grand nid-de-poule sur Main St qui doit être réparé.");
        reporting.setStatus(StatusSignalement.valueOf("PENDING"));

        reportingDto = new ReportingDto();
        reportingDto.setId(1L);
        reportingDto.setTitle("Pothole on Main St");
        reportingDto.setDescription("There is a large pothole on Main St that needs repair.");
        reportingDto.setStatus("OPEN");
    }

    @Test
    void testCreateReporting() {
        when(reportingMapper.fromReportingDto(reportingDto)).thenReturn(reporting);
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        when(reportingMapper.toReportingDto(reporting)).thenReturn(reportingDto);

        ReportingDto createdDto = reportingService.createReporting(reportingDto);
        assert createdDto != null;
        assertEquals(reportingDto.getId(), createdDto.getId());
        assertEquals(reportingDto.getTitle(), createdDto.getTitle());
        assertEquals(reportingDto.getDescription(), createdDto.getDescription());
        assertEquals(reportingDto.getStatus(), createdDto.getStatus());

        verify(reportingRepository).save(reporting);
    }

    @Test
    void testGetReportingById() {
        when(reportingRepository.findById(1L)).thenReturn(java.util.Optional.of(reporting));
        when(reportingMapper.toReportingDto(reporting)).thenReturn(reportingDto);

        ReportingDto foundDto = reportingService.getReportingById(1L);
        assert foundDto != null;
        assertEquals(reportingDto.getId(), foundDto.getId());
        assertEquals(reportingDto.getTitle(), foundDto.getTitle());
        assertEquals(reportingDto.getDescription(), foundDto.getDescription());
        assertEquals(reportingDto.getStatus(), foundDto.getStatus());

        verify(reportingRepository).findById(1L);
    }

    @Test
    void testGetReportingById_NotFound() {
        when(reportingRepository.findById(1L)).thenReturn(java.util.Optional.empty());
       assertThrows(RequestException.class, () -> reportingService.getReportingById(1L));
        verify(reportingRepository).findById(1L);
    }

    @Test
    void testUpdateReporting() {
        when(reportingRepository.findById(1L)).thenReturn(java.util.Optional.of(reporting));
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        when(reportingMapper.toReportingDto(reporting)).thenReturn(reportingDto);

        ReportingDto updatedDto = reportingService.updateReporting(1L, reportingDto);
        assertEquals(reportingDto.getId(), updatedDto.getId());
        assertEquals(reportingDto.getTitle(), updatedDto.getTitle());
        assertEquals(reportingDto.getDescription(), updatedDto.getDescription());
        assertEquals(reportingDto.getStatus(), updatedDto.getStatus());

        verify(reportingRepository).findById(1L);
        verify(reportingRepository).save(reporting);
    }

    @Test
    void testUpdateReporting_NotFound() {
        when(reportingRepository.findById(1L)).thenReturn(java.util.Optional.empty());
      //  doThrow(EntityNotFoundException.class).when(reportingRepository).findById(1L);
        assertThrows(EntityNotFoundException.class, () -> reportingService.updateReporting(1L, reportingDto));
        verify(reportingRepository).findById(1L);
    }

    @Test
    void testDeleteReporting() {
        doNothing().when(reportingRepository).deleteById(1L);
        reportingService.deleteReporting(1L);
        verify(reportingRepository).deleteById(1L);
    }

    void testDeleteReporting_NotFound() {
        when(reportingRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> reportingService.deleteReporting(1L));
        verify(reportingRepository).deleteById(1L);
    }

    @Test
    void testGetAllReportings() {
        when(reportingRepository.findAll()).thenReturn(java.util.List.of(reporting));
        when(reportingMapper.toReportingDto(reporting)).thenReturn(reportingDto);

        java.util.List<ReportingDto> reportingDtos = reportingService.getAllReportings();
        assert reportingDtos != null;
        assert reportingDtos.size() == 1;
        assertEquals(reportingDto.getId(), reportingDtos.get(0).getId());
        assertEquals(reportingDto.getTitle(), reportingDtos.get(0).getTitle());
        assertEquals(reportingDto.getDescription(), reportingDtos.get(0).getDescription());
        assertEquals(reportingDto.getStatus(), reportingDtos.get(0).getStatus());

        verify(reportingRepository).findAll();
    }

    @Test
    void testGetAllReportingsByMunicipality() {
        when(reportingRepository.findAllByMunicipalityId(1L)).thenReturn(java.util.List.of(reporting));
        when(reportingMapper.toReportingDto(reporting)).thenReturn(reportingDto);

        java.util.List<ReportingDto> reportingDtos = reportingService.getAllReportingsByMunicipality(1L);
        assert reportingDtos != null;
        assert reportingDtos.size() == 1;
        assertEquals(reportingDto.getId(), reportingDtos.get(0).getId());
        assertEquals(reportingDto.getTitle(), reportingDtos.get(0).getTitle());
        assertEquals(reportingDto.getDescription(), reportingDtos.get(0).getDescription());
        assertEquals(reportingDto.getStatus(), reportingDtos.get(0).getStatus());

        verify(reportingRepository).findAllByMunicipalityId(1L);
    }
}
