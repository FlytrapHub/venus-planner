package com.flytrap.venusplanner.api.plan.business.service;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan.domain.RecurringOption;
import com.flytrap.venusplanner.api.plan.infrastructure.repository.PlanRepository;
import com.flytrap.venusplanner.api.plan.infrastructure.repository.RecurringOptionRepository;
import com.flytrap.venusplanner.api.plan.presentation.dto.request.PlanCreateRequest;
import com.flytrap.venusplanner.api.plan.util.DateTimeUtils;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.domain.Study;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanRepository planRepository;
    private final RecurringOptionRepository recurringOptionRepository;

    public Long savePlan(Study study, PlanCategory planCategory, PlanCreateRequest request) {
        Plan plan = request.toEntity(study, planCategory);
        planRepository.save(plan);

        if (plan.getRecurringOption() != null) {
            List<Plan> recurringPlan = createPlansWithRecurringPlan(plan);
            planRepository.saveAll(recurringPlan);
        }

        return plan.getId();
    }

    private List<Plan> createPlansWithRecurringPlan(Plan planTemplate) {
        int recurrenceCount = planTemplate.getRecurringOption().getRecurrenceCount();
        ZonedDateTime startTimeZDT = DateTimeUtils.toZonedDateTime(planTemplate.getStartTime());
        ZonedDateTime endTimeZDT = DateTimeUtils.toZonedDateTime(planTemplate.getEndTime());
        ChronoUnit chronoUnit = planTemplate.getRecurringOption().getFrequency().getChronoUnit();

        List<Plan> createdPlans = new ArrayList<>();
        for (int i = 1; i < recurrenceCount; i++) {
            Instant startTime = DateTimeUtils.toInstant(startTimeZDT.plus(i, chronoUnit));
            Instant endTime = DateTimeUtils.toInstant(endTimeZDT.plus(i, chronoUnit));

            Plan newPlan = Plan.builder()
                    .study(planTemplate.getStudy())
                    .planCategory(planTemplate.getPlanCategory())
                    .recurringOption(planTemplate.getRecurringOption())
                    .title(planTemplate.getTitle())
                    .description(planTemplate.getDescription())
                    .startTime(startTime)
                    .endTime(endTime)
                    .notificationTime(planTemplate.getNotificationTime())
                    .build();

            createdPlans.add(planRepository.save(newPlan));
        }

        return createdPlans;
    }

    public List<Plan> findAllByStudyIdAndYearAndMonth(Long studyId, int year, int month) {
        return planRepository.findAllByStudyIdAndYearAndMonth(studyId, year, month);
    }

    @Transactional
    public void deleteById(Long planId) {
        //TODO: 멤버의 권한 검증 로직
        //TODO: plan이 없을 때 예외처리
        planRepository.deleteById(planId);
    }

    public void deleteAllByRecurringId(Long planId) {
        //Todo: plan 없을 때 예외처리
        Plan plan = planRepository.findById(planId).get();
        RecurringOption recurringOption = plan.getRecurringOption();
        if (recurringOption != null) {
            planRepository.deleteAllByRecurringOptionId(recurringOption.getId());
            recurringOptionRepository.deleteById(recurringOption.getId());
        } else {
            planRepository.deleteById(planId);
        }
    }
}
