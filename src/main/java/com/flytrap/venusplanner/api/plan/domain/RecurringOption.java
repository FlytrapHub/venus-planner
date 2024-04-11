package com.flytrap.venusplanner.api.plan.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Frequency frequency;

    @Enumerated(EnumType.STRING)
    @NotNull
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

    public void calculate(Instant startDate) {
        //TODO:(의견1) endOption에서 계산 로직 실행
        if (recurrenceCount != null && endDate != null) {
            return;
        }

        if (endOption == EndOption.COUNT && recurrenceCount != null) {
            this.endDate = frequency.calculateEndDate(startDate, recurrenceCount);
        } else if (endOption == EndOption.DATE && endDate != null) {
            this.recurrenceCount = frequency.calculateCount(startDate, endDate);
        }
    }
}
