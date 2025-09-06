package com.signalement.dao;

import com.signalement.entity.WreckCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WreckCategoryRepository extends JpaRepository<WreckCategory, Long> {
}
