package com.flytrap.venusplanner.acceptance.plan_category;

import static com.flytrap.venusplanner.global.step.PlanCategoryStep.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.flytrap.venusplanner.api.plan_category.presentation.dto.PlanCategoryCreateDto;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.request.PlanCategoryUpdateRequest;
import com.flytrap.venusplanner.api.plan_category.presentation.dto.response.PlanCategoryReadResponse;
import com.flytrap.venusplanner.global.AcceptanceTest;
import com.flytrap.venusplanner.global.fixture.PlanCategoryFixture;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("[인수테스트] PlanCategory CRUD 성공 케이스")
class PlanCategoryTest extends AcceptanceTest {

    @Test
    void 일정_카테고리_생성_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청

        // when
        var response = 일정_카테고리_생성_요청(
                1L,
                PlanCategoryFixture.모임.toDto()
        );

        // then
        일정_카테고리_생성_정보_검증(response);
        응답_상태코드_검증(response, HttpStatus.CREATED);
    }

    @Test
    void 일정_카테고리_조회_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청
        일정_카테고리_생성_요청(
                1L,
                PlanCategoryFixture.모임.toDto()
        );
        일정_카테고리_생성_요청(
                1L,
                PlanCategoryFixture.회의.toDto()
        );

        // when
        var response = 일정_카테고리_조회_요청(1L);

        // then
        일정_카테고리_조회_정보_검증(response);
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 일정_카테고리_타이틀_수정_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청
        PlanCategoryFixture planCategoryFixture = PlanCategoryFixture.모임;

        일정_카테고리_생성_요청(
                1L,
                planCategoryFixture.toDto()
        );

        String updatedTitle = "정기 모임";
        PlanCategoryUpdateRequest dto = new PlanCategoryUpdateRequest(
                updatedTitle,
                planCategoryFixture.getFontColor(),
                planCategoryFixture.getBackgroundColor()
        );

        // when
        var response = 일정_카테고리_수정_요청(1L, 1L, dto);

        // then
        일정_카테고리_타이틀_수정_정보_검증(updatedTitle, planCategoryFixture);
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 일정_카테고리_폰트_색상_수정_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청
        PlanCategoryFixture planCategoryFixture = PlanCategoryFixture.모임;

        일정_카테고리_생성_요청(
                1L,
                planCategoryFixture.toDto()
        );

        String updatedFontColor = "#FFFF00";
        PlanCategoryUpdateRequest dto = new PlanCategoryUpdateRequest(
                planCategoryFixture.getTitle(),
                updatedFontColor,
                planCategoryFixture.getBackgroundColor()
        );

        // when
        var response = 일정_카테고리_수정_요청(1L, 1L, dto);

        // then
        일정_카테고리_폰트_색상_수정_정보_검증(updatedFontColor, planCategoryFixture);
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 일정_카테고리_배경_색상_수정_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청
        PlanCategoryFixture planCategoryFixture = PlanCategoryFixture.모임;

        일정_카테고리_생성_요청(
                1L,
                planCategoryFixture.toDto()
        );

        String updatedBackgroundColor = "#0000FF";
        PlanCategoryUpdateRequest dto = new PlanCategoryUpdateRequest(
                planCategoryFixture.getTitle(),
                planCategoryFixture.getFontColor(),
                updatedBackgroundColor
        );

        // when
        var response = 일정_카테고리_수정_요청(1L, 1L, dto);

        // then
        일정_카테고리_배경_색상_수정_정보_검증(updatedBackgroundColor, planCategoryFixture);
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 일정_카테고리_삭제_요청_시_성공한다() {
        // given
        // TODO: 로그인 요청
        일정_카테고리_생성_요청(
                1L,
                PlanCategoryFixture.모임.toDto()
        );

        // when
        var response = 일정_카테고리_삭제_요청(1L, 1L);

        // then
        일정_카테고리_삭제_정보_검증();
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    private void 일정_카테고리_생성_정보_검증(ExtractableResponse<Response> response) {
        PlanCategoryCreateDto.Response actual = response.jsonPath()
                .getObject(".", PlanCategoryCreateDto.Response.class);
        Long expected = 1L;

        assertThat(actual).hasFieldOrPropertyWithValue("planCategoryId", expected);
    }

    private void 일정_카테고리_조회_정보_검증(ExtractableResponse<Response> response) {
        List<PlanCategoryReadResponse> actual = response.jsonPath().getList(".", PlanCategoryReadResponse.class);

        assertAll(
                () -> assertThat(actual).hasSize(2),
                () -> assertThat(actual.get(0))
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("title", PlanCategoryFixture.모임.getTitle())
                        .hasFieldOrPropertyWithValue("fontColor", PlanCategoryFixture.모임.getFontColor())
                        .hasFieldOrPropertyWithValue("backgroundColor", PlanCategoryFixture.모임.getBackgroundColor())
        );
    }

    private void 일정_카테고리_타이틀_수정_정보_검증(String updatedTitle, PlanCategoryFixture originalObject) {
        PlanCategoryReadResponse response = 일정_카테고리_조회_요청(1L).jsonPath()
                .getObject("[0]", PlanCategoryReadResponse.class);

        assertAll(
                () -> assertThat(response)
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("title", updatedTitle)
                        .hasFieldOrPropertyWithValue("fontColor", originalObject.getFontColor())
                        .hasFieldOrPropertyWithValue("backgroundColor", originalObject.getBackgroundColor())
        );
    }

    private void 일정_카테고리_폰트_색상_수정_정보_검증(String updatedFontColor, PlanCategoryFixture originalObject) {
        PlanCategoryReadResponse response = 일정_카테고리_조회_요청(1L).jsonPath()
                .getObject("[0]", PlanCategoryReadResponse.class);

        assertAll(
                () -> assertThat(response)
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("title", originalObject.getTitle())
                        .hasFieldOrPropertyWithValue("fontColor", updatedFontColor)
                        .hasFieldOrPropertyWithValue("backgroundColor", originalObject.getBackgroundColor())
        );
    }

    private void 일정_카테고리_배경_색상_수정_정보_검증(String updatedBackgroundColor, PlanCategoryFixture originalObject) {
        PlanCategoryReadResponse response = 일정_카테고리_조회_요청(1L).jsonPath()
                .getObject("[0]", PlanCategoryReadResponse.class);

        assertAll(
                () -> assertThat(response)
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("title", originalObject.getTitle())
                        .hasFieldOrPropertyWithValue("fontColor", originalObject.getFontColor())
                        .hasFieldOrPropertyWithValue("backgroundColor", updatedBackgroundColor)
        );
    }

    private void 일정_카테고리_삭제_정보_검증() {
        List<Object> response = 일정_카테고리_조회_요청(1L).jsonPath().getList(".");

        assertThat(response).hasSize(0);
    }
}
