package com.flytrap.venusplanner.global.fixture;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.study.domain.Study;

public class JoinRequestFixture {

    public static JoinRequest 신규_가입_요청(Study study, Member member) {
        return JoinRequest.create(study.getId(), member.getId());
    }

    public static JoinRequest 수락된_가입_요청(Study study, Member member) {
        return JoinRequest.builder()
                .studyId(study.getId())
                .memberId(member.getId())
                .state(JoinRequestState.ACCEPT)
                .build();
    }

    public static JoinRequest 거절된_가입_요청(Study study, Member member) {
        return JoinRequest.builder()
                .studyId(study.getId())
                .memberId(member.getId())
                .state(JoinRequestState.REJECT)
                .build();
    }
}
