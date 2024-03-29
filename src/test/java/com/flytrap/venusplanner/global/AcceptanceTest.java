package com.flytrap.venusplanner.global;

import static org.assertj.core.api.Assertions.assertThat;

import com.flytrap.venusplanner.api.admin.domain.AdminProperties;
import com.flytrap.venusplanner.api.admin.presentation.dto.request.AdminSignInRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("classpath:reset.sql")
public abstract class AcceptanceTest {

    @Autowired
    protected AdminProperties adminProperties;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected static RequestSpecification givenJsonRequest() {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected ExtractableResponse<Response> 어드민_로그인_요청() {
        return givenJsonRequest()
                .body(new AdminSignInRequest(adminProperties.code()))
                .when().post("/api/v1/admin/sign-in")
                .then().log().all().extract();
    }

    protected void 응답_상태코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }
}
