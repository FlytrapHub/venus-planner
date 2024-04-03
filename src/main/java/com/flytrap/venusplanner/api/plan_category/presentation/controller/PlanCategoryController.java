package com.flytrap.venusplanner.api.plan_category.presentation.controller;

import com.flytrap.venusplanner.api.plan_category.business.service.PlanCategoryService;
import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.PlanCategoryCreateDto;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.request.PlanCategoryUpdateRequest;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.response.PlanCategoryReadResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanCategoryController {

    private final PlanCategoryService planCategoryService;

    @PostMapping("/api/v1/studies/{studyId}/categories")
    public ResponseEntity<PlanCategoryCreateDto.Response> createPlanCategory(
            @PathVariable("studyId") Long studyId,
            @Valid @RequestBody PlanCategoryCreateDto.Request request
    ) {
        Long categoryId = planCategoryService.savePlanCategory(request.toEntity(studyId));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlanCategoryCreateDto.Response.from(categoryId));
    }

    @GetMapping("/api/v1/studies/{studyId}/categories")
    public ResponseEntity<List<PlanCategoryReadResponse>> readPlanCategories(
            @PathVariable("studyId") Long studyId
    ) {
        List<PlanCategory> planCategories = planCategoryService.findAllByStudyId(studyId);

        return ResponseEntity.ok()
                .body(PlanCategoryReadResponse.from(planCategories));
    }

    @PatchMapping("/api/v1/studies/{studyId}/categories/{categoryId}")
    public ResponseEntity<Void> updatePlanCategory(
            @PathVariable("categoryId") Long categoryId,
            @Valid @RequestBody PlanCategoryUpdateRequest request
    ) {
        // TODO 회원의 수정 권한을 검증하는 로직 구현
        planCategoryService.update(categoryId, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/v1/studies/{studyId}/categories/{categoryId}")
    public ResponseEntity<Void> deletePlanCategory(
            @PathVariable("categoryId") Long categoryId
    ) {
        // TODO 회원의 삭제 권한을 검증하는 로직 구현
        planCategoryService.deleteById(categoryId);

        return ResponseEntity.ok()
                .build();
    }
}
