package com.flytrap.venusplanner.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.flytrap.venusplanner.acceptance.common.SessionCookie;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(properties = {"auth.session.sessionName=login_session::"})
@Sql("classpath:reset.sql")
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Value("${server.servlet.session.cookie.name}")
    protected String sessionCookieName;

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);
        redis.start();
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
    }

    @BeforeAll
    void setUp() {
        RestAssured.port = port;
    }

    public SessionCookie 테스트_로그인(Long memberId) {
        String sessionCookieValue = givenJsonRequest()
                .body(memberId)
                .when().post("/api/v1/test/sign-in")
                .then().extract()
                .cookie(sessionCookieName);

        return new SessionCookie(sessionCookieName, sessionCookieValue);
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

    protected void 응답_코드는_400_BAD_REQUEST_를_반환한다(ExtractableResponse<Response> response) {
        응답_상태코드_검증(response, HttpStatus.BAD_REQUEST);
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
