package com.flytrap.venusplanner.api.member.exception;

import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.domain.OAuthPlatformType;
import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;

public class MemberExceptionType extends GeneralExceptionType {

    public static CustomException MemberNotFoundException() {
        return DomainNotFoundException(Member.class);
    }

    public static CustomException MemberNotFoundException(Long domainId) {
        return DomainNotFoundException(Member.class, domainId);
    }

    public static CustomException NoSuchOauthPlatformTypeException(String mismatchedTypeName) {
        return NoSuchEnumTypeException(OAuthPlatformType.class, mismatchedTypeName);
    }

}
