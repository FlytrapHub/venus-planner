package com.flytrap.venusplanner.api.plan.presentation.controller;

import com.flytrap.venusplanner.api.plan.domain.Plan;
import com.flytrap.venusplanner.api.plan.presentation.dto.request.PlanReadConditionRequest;
import com.flytrap.venusplanner.api.plan.presentation.dto.request.PlanCreateRequest;
import com.flytrap.venusplanner.api.plan.presentation.dto.response.PlanReadResponse;
import com.flytrap.venusplanner.api.plan.business.service.StudyPlanFacadeService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlanController {

    private final StudyPlanFacadeService studyPlanFacadeService;

    @PostMapping("/api/v1/studies/{studyId}/plans")
    public ResponseEntity<Long> createPlan(@PathVariable Long studyId,
                                           @Valid @RequestBody PlanCreateRequest request) {
        Long memberId = 1L;
        Long planId = studyPlanFacadeService.savePlan(studyId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(planId);
    }

    @GetMapping("/api/v1/studies/{studyId}/plans")
    public ResponseEntity<List<PlanReadResponse>> readPlans(
            @PathVariable Long studyId,
            @Valid @ModelAttribute PlanReadConditionRequest params) {
        List<Plan> studyPlans = studyPlanFacadeService.findAllBy(studyId, params.year(), params.month());

        return ResponseEntity.ok()
                .body(PlanReadResponse.from(studyPlans));
    }

    @DeleteMapping("/api/v1/studies/{studyId}/plans/{planId}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long planId,
            @RequestParam boolean applyAll
    ) {
        studyPlanFacadeService.delete(planId, applyAll);

        return ResponseEntity.ok()
                .build();
    }
}
