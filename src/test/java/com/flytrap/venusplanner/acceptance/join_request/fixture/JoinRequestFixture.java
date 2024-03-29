package com.flytrap.venusplanner.acceptance.join_request.fixture;

import com.flytrap.venusplanner.api.access.domain.Permission;
import com.flytrap.venusplanner.api.access.domain.Roll;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.domain.OAuthPlatformType;
import com.flytrap.venusplanner.api.member_study.domain.MemberStudy;
import com.flytrap.venusplanner.api.study.domain.Study;

public class JoinRequestFixture {

    public static Member 리더() {
        return Member.builder()
                .oauthPk("00000")
                .oauthPlatformId(OAuthPlatformType.GITHUB.getId())
                .email("leader@venus.test")
                .nickname("leader")
                .profileImageUrl("https://test.com")
                .build();
    }

    public static Member 멤버_01() {
        return Member.builder()
                .oauthPk("11111")
                .oauthPlatformId(OAuthPlatformType.GITHUB.getId())
                .email("member01@venus.test")
                .nickname("member01")
                .profileImageUrl("https://test.com")
                .build();
    }

    public static Study 알고리즘_스터디() {
        return new Study("알고리즘 챌린지", "알고리즘 실력을 쌓아갑니다.");
    }

    public static MemberStudy 리더_멤버_스터디(Study study, Member leader) {
        return MemberStudy.fromLeader(leader.getId(), study);
    }

    public static MemberStudy 멤버_스터디(Study study, Member member) {
        return MemberStudy.builder()
                .memberId(member.getId())
                .study(study)
                .rollId(Roll.MEMBER.getId())
                .permissionId(Permission.NONE.getId())
                .build();
    }

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
