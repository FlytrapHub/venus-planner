package com.flytrap.venusplanner.api.plan.presentation.dto.request;

import com.flytrap.venusplanner.api.plan.domain.EndOption;
import com.flytrap.venusplanner.api.plan.domain.Frequency;
import com.flytrap.venusplanner.api.plan.domain.RecurringOption;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record RecurringOptionRequest(
        @NotNull
        Frequency frequency,

        @NotNull
        EndOption endOption,
        Integer recurrenceCount,
        Instant endDate
) {
    public RecurringOption toEntity() {
        return RecurringOption.builder()
                .frequency(frequency)
                .endOption(endOption)
                .recurrenceCount(recurrenceCount)
                .endDate(endDate)
                .build();
    }
}
