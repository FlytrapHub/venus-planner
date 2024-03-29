package com.flytrap.venusplanner.acceptance.admin;

import com.flytrap.venusplanner.api.admin.presentation.dto.request.AdminSignInRequest;
import com.flytrap.venusplanner.global.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("[인수테스트] Admin 로그인 성공/실패 케이스")
public class AdminTest extends AcceptanceTest {

    @Test
    void 어드민_로그인_시_성공한다() {
        // when
        var response = 어드민_로그인_요청();

        // then
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    @Test
    void 어드민_로그인_시_잘못된_코드로_요청하면_실패한다() {
        // when
        var response = givenJsonRequest()
                .body(new AdminSignInRequest("code"))
                .when().post("/api/v1/admin/sign-in")
                .then().log().all().extract();

        // then
        응답_상태코드_검증(response, HttpStatus.UNAUTHORIZED);
    }
}
