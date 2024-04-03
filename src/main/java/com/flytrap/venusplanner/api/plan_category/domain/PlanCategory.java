package com.flytrap.venusplanner.api.plan_category.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class PlanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long studyId;

    @NotNull
    private String title;

    @NotNull
    @Embedded
    @AttributeOverride(
            name = "code",
            column = @Column(name = "font_color")
    )
    private ColorCode fontColor;

    @NotNull
    @Embedded
    @AttributeOverride(
            name = "code",
            column = @Column(name = "background_color")
    )
    private ColorCode backgroundColor;

    @Builder
    private PlanCategory(Long studyId, String title, String fontColor, String backgroundColor) {
        this.studyId = studyId;
        this.title = title;
        this.fontColor = ColorCode.from(fontColor);
        this.backgroundColor = ColorCode.from(backgroundColor);
    }

    public void update(String title, String fontColor, String backgroundColor) {
        this.title = title;
        this.fontColor = ColorCode.from(fontColor);
        this.backgroundColor = ColorCode.from(backgroundColor);
    }
}
