package com.flytrap.venusplanner.api.study_plan.presentation.dto.request;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan.domain.RecurringOption;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.domain.Study;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Optional;

public record StudyPlanCreateRequest(
        @NotNull
        Long categoryId,

        @Size(max = 100)
        String title,

        @Size(max = 255)
        String description,

        @NotNull
        Instant startTime,

        @NotNull
        Instant endTime,

        @Future
        Instant notificationTime,
        RecurringOptionRequest recurringOption
) {
    public Plan toEntity(Study study, PlanCategory planCategory) {
        RecurringOption recurringOptionEntity = Optional.ofNullable(recurringOption)
                .map(RecurringOptionRequest::toEntity)
                .orElse(null);

        return Plan.builder()
                .study(study)
                .planCategory(planCategory)
                .recurringOption(recurringOptionEntity)
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .notificationTime(notificationTime)
                .build();
    }
}
