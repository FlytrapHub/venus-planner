package com.flytrap.venusplanner.acceptance.join_request.step;

import com.flytrap.venusplanner.acceptance.AcceptanceTest;
import com.flytrap.venusplanner.acceptance.common.SessionCookie;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class JoinRequestStep extends AcceptanceTest {

    public static ExtractableResponse<Response> 스터디_가입_요청(Long studyId, SessionCookie sessionCookie) {
        return givenJsonRequest()
                .cookie(sessionCookie.name(), sessionCookie.value())
                .when().post("/api/v1/studies/{studyId}/join-requests", studyId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 스터디_가입_요청_수락(Long studyId, Long requestId, SessionCookie sessionCookie) {
        return givenJsonRequest()
                .cookie(sessionCookie.name(), sessionCookie.value())
                .when().patch("/api/v1/studies/{studyId}/join-requests/{requestId}/accept", studyId, requestId)
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 스터디_가입_요청_거절(Long studyId, Long requestId, SessionCookie sessionCookie) {
        return givenJsonRequest()
                .cookie(sessionCookie.name(), sessionCookie.value())
                .when().patch("/api/v1/studies/{studyId}/join-requests/{requestId}/reject", studyId, requestId)
                .then().log().all().extract();
    }

}
