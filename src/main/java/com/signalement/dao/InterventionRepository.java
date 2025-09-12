package com.signalement.dao;

import com.signalement.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    Optional<Intervention> findByReportingId(Long signalementId);

    List<Intervention> findAllByMunicipalAgentId(Long municipalAgentId);
}
