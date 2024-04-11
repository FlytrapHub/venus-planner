package com.flytrap.venusplanner.api.plan.business.service;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan.presentation.dto.request.PlanCreateRequest;
import com.flytrap.venusplanner.api.plan_category.business.service.PlanCategoryService;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.business.service.StudyValid;
import com.flytrap.venusplanner.api.study.domain.Study;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyPlanFacadeService {

    private final StudyValid studyValid;
    private final PlanCategoryService planCategoryService;
    private final PlanService planService;

    @Transactional
    public Long savePlan(Long studyId, PlanCreateRequest request) {
        Study study = studyValid.findById(studyId);
        PlanCategory planCategory = planCategoryService.findById(request.categoryId());
        return planService.savePlan(study, planCategory, request);
    }

    @Transactional
    public List<Plan> findAllBy(Long studyId, int year, int month) {
        Study study = studyValid.findById(studyId);
        List<Plan> plans = planService.findAllByStudyIdAndYearAndMonth(studyId, year, month);

        return plans;
    }

    @Transactional
    public void delete(Long planId, boolean applyAll) {
        if (applyAll) {
            planService.deleteAllByRecurringId(planId);
        } else {
            planService.deleteById(planId);
        }
    }
}
