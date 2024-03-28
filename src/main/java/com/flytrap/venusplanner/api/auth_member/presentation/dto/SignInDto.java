package com.flytrap.venusplanner.api.auth_member.presentation.dto;

import com.flytrap.venusplanner.api.member.domain.Member;

public class SignInDto {

    public record Request(
            String code
    ) {
    }

    public record Response(
            String email,
            String profileUrl,
            String nickName
    ) {
        public static Response from(Member member) {
            return new Response(
                    member.getEmail(),
                    member.getProfileImageUrl(),
                    member.getNickname()
            );
        }
    }
}
