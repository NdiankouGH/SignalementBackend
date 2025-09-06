package com.signalement.dao;

import com.signalement.entity.MunicipalAgent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalAgentRepository extends JpaRepository<MunicipalAgent, Long> {
}
