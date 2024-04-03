package com.flytrap.venusplanner.global.step;

import com.flytrap.venusplanner.api.plan_category.presentation.dto.PlanCategoryCreateDto;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.request.PlanCategoryUpdateRequest;
import com.flytrap.venusplanner.global.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class PlanCategoryStep extends AcceptanceTest {

    public static ExtractableResponse<Response> 일정_카테고리_생성_요청(Long studyId, PlanCategoryCreateDto.Request dto) {
        return givenJsonRequest()
                .body(dto)
                .when().post("/api/v1/studies/{studyId}/categories", studyId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_카테고리_조회_요청(Long studyId) {
        return givenJsonRequest()
                .when().get("/api/v1/studies/{studyId}/categories", studyId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_카테고리_수정_요청(
            Long studyId, Long categoryId, PlanCategoryUpdateRequest dto
    ) {
        return givenJsonRequest()
                .body(dto)
                .when().patch("/api/v1/studies/{studyId}/categories/{categoryId}", studyId, categoryId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 일정_카테고리_삭제_요청(Long studyId, Long categoryId) {
        return givenJsonRequest()
                .when().delete("/api/v1/studies/{studyId}/categories/{categoryId}", studyId, categoryId)
                .then().log().all().extract();
    }
}
