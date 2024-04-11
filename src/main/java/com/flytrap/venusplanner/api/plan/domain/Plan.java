package com.flytrap.venusplanner.api.plan.domain;

import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.domain.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_category_id")
    private PlanCategory planCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_option_id")
    @Cascade(CascadeType.PERSIST)
    private RecurringOption recurringOption;

    private String title;
    private String description;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    private Instant notificationTime;

    @Builder
    private Plan(Study study, PlanCategory planCategory, RecurringOption recurringOption, String title,
                 String description,
                 Instant startTime, Instant endTime, Instant notificationTime) {
        this.study = study;
        this.planCategory = planCategory;
        this.recurringOption = recurringOption;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.notificationTime = notificationTime;
    }

    @PreUpdate
    public void validateTitle() {
        final String DEFAULT_TILE = "새로운 일정";

        if (title == null || title.isEmpty()) {
            title = DEFAULT_TILE;
        }
    }

    @PrePersist
    private void beforeSave() {
        if (recurringOption != null) {
            recurringOption.calculate(startTime);
        }
        validateTitle();
    }
}
