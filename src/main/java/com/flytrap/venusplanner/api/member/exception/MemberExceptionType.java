package com.flytrap.venusplanner.api.member.exception;

import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.domain.OAuthPlatformType;
import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;

public class MemberExceptionType extends GeneralExceptionType {

    public static CustomException MEMBER_NOT_FOUND_EXCEPTION() {
        return DOMAIN_NOT_FOUND_EXCEPTION(Member.class);
    }

    public static CustomException MEMBER_NOT_FOUND_EXCEPTION(Long domainId) {
        return DOMAIN_NOT_FOUND_EXCEPTION(Member.class, domainId);
    }

    public static CustomException NO_SUCH_OAUTH_PLATFORM_TYPE_EXCEPTION(String mismatchedTypeName) {
        return NO_SUCH_ENUM_TYPE_EXCEPTION(OAuthPlatformType.class, mismatchedTypeName);
    }

}
