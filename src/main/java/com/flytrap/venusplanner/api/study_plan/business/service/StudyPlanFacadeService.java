package com.flytrap.venusplanner.api.study_plan.business.service;

import com.flytrap.venusplanner.api.plan.business.service.PlanService;
import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan_category.business.service.PlanCategoryService;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.business.service.StudyService;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study_plan.presentation.dto.request.StudyPlanCreateRequest;
import com.flytrap.venusplanner.api.study_plan.presentation.dto.response.StudyPlanReadResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyPlanFacadeService {

    private final StudyService studyService;
    private final PlanCategoryService planCategoryService;
    private final PlanService planService;

    public Long savePlan(Long studyId, StudyPlanCreateRequest request) {
        Study study = studyService.findById(studyId);
        PlanCategory planCategory = planCategoryService.findById(request.categoryId());
        return planService.savePlan(study, planCategory, request);
    }

    public List<StudyPlanReadResponse> findAllBy(Long studyId, int year, int month) {
        Study study = studyService.findById(studyId);
        List<Plan> plans = planService.findAllByStudyIdAndYearAndMonth(studyId, year, month);

        return StudyPlanReadResponse.from(plans);
    }

    public void deleteById(Long planId) {
        planService.deleteById(planId);
    }
}
