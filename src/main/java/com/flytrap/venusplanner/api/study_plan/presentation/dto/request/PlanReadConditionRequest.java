package com.flytrap.venusplanner.api.study_plan.presentation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PlanReadConditionRequest(
        @Min(2020)
        @Max(2124)
        int year,

        @Min(1)
        @Max(12)
        int month
) {
}
