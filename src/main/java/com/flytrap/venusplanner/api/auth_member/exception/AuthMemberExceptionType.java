package com.flytrap.venusplanner.api.auth_member.exception;

import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;

public class AuthMemberExceptionType extends GeneralExceptionType {

    public static CustomException GITHUB_OAUTH_REQUEST_FAILURE_EXCEPTION(String message) {
        return API_REQUEST_FAILURE_EXCEPTION(message);
    }

}
