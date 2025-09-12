package com.signalement.dao;

import com.signalement.entity.MunicipalAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalAgentRepository extends JpaRepository<MunicipalAgent, Long> {
}
