package com.signalement.dao;

import com.signalement.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterventionRepository extends JpaRepository<Intervention, Long> {
    Intervention findBySignalementId(Long signalementId);

    List<Intervention> findAllByMunicipalAgentId(Long municipalAgentId);
}
