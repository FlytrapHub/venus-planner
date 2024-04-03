package com.flytrap.venusplanner.api.study_plan.presentation.dto.response;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import java.time.Instant;
import java.util.List;

public record StudyPlanReadResponse(
        Long planId,
        Long categoryId,
        String title,
        Instant startTime,
        Instant endTime
) {
    public static List<StudyPlanReadResponse> from(List<Plan> plans) {
       return plans.stream()
               .map(StudyPlanReadResponse::from)
               .toList();
    }

    private static StudyPlanReadResponse from(Plan plan) {
        return new StudyPlanReadResponse(plan.getId(), plan.getPlanCategory().getId(), plan.getTitle(), plan.getStartTime(), plan.getEndTime());
    }
}
