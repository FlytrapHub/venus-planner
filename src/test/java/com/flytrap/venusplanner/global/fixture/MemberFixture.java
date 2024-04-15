package com.flytrap.venusplanner.global.fixture;

import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.domain.OAuthPlatformType;

public class MemberFixture {

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
}
