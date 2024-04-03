package com.flytrap.venusplanner.api.plan.business.service;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan.infrastructure.repository.PlanRepository;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study_plan.presentation.dto.request.StudyPlanCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanRepository planRepository;

    public Long savePlan(Study study, PlanCategory planCategory, StudyPlanCreateRequest request) {
        //TODO: 반복 옵션 설정시 DB에 여러 plan 저장 로직 추가
        Plan plan = request.toEntity(study, planCategory);
        planRepository.save(plan);

        return plan.getId();
    }

    public List<Plan> findAllByStudyIdAndYearAndMonth(Long studyId, int year, int month) {
        return planRepository.findAllByStudyIdAndYearAndMonth(studyId, year, month);
    }

    @Transactional
    public void deleteById(Long planId) {
        //TODO: 멤버의 권한 검증 로직
        //TODO: 반복옵션 전체 삭제 여부
        //TODO: plan이 없을 때 예외처리
        planRepository.deleteById(planId);
    }
}
