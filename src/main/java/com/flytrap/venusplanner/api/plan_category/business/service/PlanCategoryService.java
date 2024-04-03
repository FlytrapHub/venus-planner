package com.flytrap.venusplanner.api.plan_category.business.service;

import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.plan_category.infrastructure.repository.PlanCategoryRepository;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.request.PlanCategoryUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanCategoryService {

    private final PlanCategoryRepository planCategoryRepository;

    @Transactional
    public Long savePlanCategory(PlanCategory planCategory) {
        planCategoryRepository.save(planCategory);
        return planCategory.getId();
    }

    public PlanCategory findById(Long id) {
        //TODO: optional null일 때 에러처리
        return planCategoryRepository.findById(id).get();
    }

    public List<PlanCategory> findAllByStudyId(Long studyId) {
        return planCategoryRepository.findAllByStudyId(studyId);
    }

    @Transactional
    public void update(Long categoryId, PlanCategoryUpdateRequest request) {
        PlanCategory planCategory = findById(categoryId);
        planCategory.update(request.title(), request.fontColor(), request.backgroundColor());
    }

    @Transactional
    public void deleteById(Long categoryId) {
        planCategoryRepository.deleteById(categoryId);
    }
}
