package com.flytrap.venusplanner.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Sql("classpath:reset.sql")
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeAll
    void setUp() {
        RestAssured.port = port;
    }

    protected static RequestSpecification givenJsonRequest() {
        return RestAssured.given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    protected void 응답_코드는_200_OK_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.OK);
    }

    protected void 응답_코드는_201_CREATED_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.CREATED);
    }

    protected void 응답_코드는_403_FORBIDDEN_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.FORBIDDEN);
    }

    protected void 응답_코드는_404_NOT_FOUND_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.NOT_FOUND);
    }

    protected void 응답_코드는_409_CONFLICT_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.CONFLICT);
    }

    private void 응답_상태코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {
        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }
}
