package com.flytrap.venusplanner.api.plan.infrastructure.repository;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query(value = "SELECT * FROM plan p WHERE p.study_id = :studyId " +
            "AND (YEAR(p.start_time) = :year AND MONTH(p.start_time) = :month) " +
            "OR (YEAR(p.end_time) = :year AND MONTH(p.end_time) = :month)",
            nativeQuery = true)
    List<Plan> findAllByStudyIdAndYearAndMonth(Long studyId, int year, int month);
}
