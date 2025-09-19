package com.signalement.dao;

import com.signalement.entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingRepository extends JpaRepository<Reporting, Long> {
    List<Reporting> findAllByCitizenId(Long citizenId);

    List<Reporting> findAllByCategory_Id(Long categoryId);


    List<Reporting> findAllByMunicipalityId(Long municipalityId);
}
