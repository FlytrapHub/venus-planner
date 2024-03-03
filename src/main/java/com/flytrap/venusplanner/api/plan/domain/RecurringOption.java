package com.flytrap.venusplanner.api.plan.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecurringOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    private EndOption endOption;
    private Integer recurrenceCount;
    private Instant endDate;

    @Builder
    public RecurringOption(Frequency frequency, EndOption endOption,
                           Integer recurrenceCount, Instant endDate) {
        this.frequency = frequency;
        this.endOption = endOption;
        this.recurrenceCount = recurrenceCount;
        this.endDate = endDate;
    }
}
