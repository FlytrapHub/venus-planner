package com.flytrap.venusplanner.api.auth_member.exception;

import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;

public class AuthMemberExceptionType extends GeneralExceptionType {

    public static CustomException GitHubOauthRequestFailureException(String message) {
        return ApiRequestFailureException(message);
    }

}
