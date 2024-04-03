package com.flytrap.venusplanner.api.plan_category.infrastructure.repository;

import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanCategoryRepository extends JpaRepository<PlanCategory, Long> {

    List<PlanCategory> findAllByStudyId(Long studyId);

}
