package com.flytrap.venusplanner.global.annotation.color_code;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ColorCodeValidator implements ConstraintValidator<ColorCode, String> {

    @Override
    public void initialize(ColorCode constraintAnnotation) {
    }

    @Override
    public boolean isValid(String colorCode, ConstraintValidatorContext context) {
        return colorCode.matches("^#[0-9A-Fa-f]{6}$");
    }
}

