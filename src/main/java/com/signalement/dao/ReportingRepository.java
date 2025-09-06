package com.signalement.dao;

import com.signalement.entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportingRepository extends JpaRepository<Reporting, Long> {
    List<Reporting> findAllByCitizenId(Long citizenId);

    List<Reporting> findAllByWreckCategoryId(Long wreckCategoryId);

    List<Reporting> findAllByMunicipalityId(Long municipalityId);
}
