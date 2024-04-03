package com.flytrap.venusplanner.api.plan_category.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ColorCode {

    private String code;

    private ColorCode(String color) {
        validateCode(color);
        this.code = color;
    }

    public static ColorCode from(String color) {
        return new ColorCode(color);
    }

    private void validateCode(String color) {
        if (!color.matches("^#[0-9A-Fa-f]{6}$")) {
            throw new IllegalArgumentException(); // TODO 커스텀 예외로 수정
        }
    }
}
