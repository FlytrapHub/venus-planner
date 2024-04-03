package com.flytrap.venusplanner.api.plan.infrastructure.repository;

import com.flytrap.venusplanner.api.plan.domain.RecurringOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringOptionRepository extends JpaRepository<RecurringOption, Long> {
}
