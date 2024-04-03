package com.flytrap.venusplanner.api.study.presentation.dto;

import com.flytrap.venusplanner.api.study.domain.Study;
import jakarta.validation.constraints.Size;

public record StudyCreateDto(
) {
    public record Request(

            @Size(min = 1, max = 100, message = "이름은 1~100자 사이로 입력 가능합니다.")
            String name,

            String description
    ) {
        public Study toEntity() {
            return new Study(
                    name,
                    description
            );
        }
    }

    public record Response(
            Long id,
            String name
    ) {
        public static Response from(Study study) {
            return new Response(
                    study.getId(),
                    study.getName()
            );
        }
    }
}
