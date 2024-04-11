package com.flytrap.venusplanner.api.plan.presentation.dto.response;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import java.time.Instant;
import java.util.List;

public record PlanReadResponse(
        Long planId,
        Long categoryId,
        String title,
        Instant startTime,
        Instant endTime
) {
    public static List<PlanReadResponse> from(List<Plan> plans) {
       return plans.stream()
               .map(PlanReadResponse::from)
               .toList();
    }

    private static PlanReadResponse from(Plan plan) {
        return new PlanReadResponse(plan.getId(), plan.getPlanCategory().getId(), plan.getTitle(), plan.getStartTime(), plan.getEndTime());
    }
}
