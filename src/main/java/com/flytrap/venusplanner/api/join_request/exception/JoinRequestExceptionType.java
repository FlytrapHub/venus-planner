package com.flytrap.venusplanner.api.join_request.exception;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;

public class JoinRequestExceptionType extends GeneralExceptionType {

    public static CustomException JoinRequestNotFoundException(Long joinRequestId) {
        return DomainNotFoundException(JoinRequest.class, joinRequestId);
    }

    public static CustomException DuplicateJoinRequestException() {
        return DuplicateDomainException("이미 가입 요청된 상태입니다.");
    }

    public static CustomException JoinRequestAlreadyHandledException() {
        return DuplicateDomainException("이미 처리된 가입 요청입니다");
    }

}
