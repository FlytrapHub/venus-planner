package com.flytrap.venusplanner.global.auth.dto;

import com.flytrap.venusplanner.api.member.domain.Member;
import java.io.Serializable;

/**
 * 로그인 후 Session에 저장되는 Member DTO입니다.
 * @param id Member의 id값
 */
public record SessionMember (
        long id
) implements Serializable {

    public static SessionMember from(Member member) {
        return new SessionMember(member.getId());
    }
}
