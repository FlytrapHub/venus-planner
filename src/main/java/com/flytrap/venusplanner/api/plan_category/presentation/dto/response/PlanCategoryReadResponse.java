package com.flytrap.venusplanner.api.plan_category.presentation.dto.response;

import com.flytrap.venusplanner.api.plan_category.domain.PlanCategory;
import java.util.List;

public record PlanCategoryReadResponse(
        Long id,
        String title,
        String fontColor,
        String backgroundColor

) {
    public static List<PlanCategoryReadResponse> from(List<PlanCategory> planCategories) {
        return planCategories.stream()
                .map(PlanCategoryReadResponse::from)
                .toList();
    }

    private static PlanCategoryReadResponse from(PlanCategory planCategory) {
        return new PlanCategoryReadResponse(
                planCategory.getId(),
                planCategory.getTitle(),
                planCategory.getFontColor().getCode(),
                planCategory.getBackgroundColor().getCode()
        );
    }
}
