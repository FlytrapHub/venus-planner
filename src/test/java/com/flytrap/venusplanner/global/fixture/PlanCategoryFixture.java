package com.flytrap.venusplanner.global.fixture;

import com.flytrap.venusplanner.api.plan_category.presentation.dto.PlanCategoryCreateDto;

public enum PlanCategoryFixture {

    모임("모임", "#FFFFFF", "#000000"),
    회의("회의", "#FF00FF", "#0000FF");

    private final String title;
    private final String fontColor;
    private final String backgroundColor;

    PlanCategoryFixture(String title, String fontColor, String backgroundColor) {
        this.title = title;
        this.fontColor = fontColor;
        this.backgroundColor = backgroundColor;
    }

    public PlanCategoryCreateDto.Request toDto() {
        return new PlanCategoryCreateDto.Request(
                this.title,
                this.fontColor,
                this.backgroundColor
        );
    }

    public String getTitle() {
        return title;
    }

    public String getFontColor() {
        return fontColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}
