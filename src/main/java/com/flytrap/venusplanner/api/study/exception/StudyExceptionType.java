package com.flytrap.venusplanner.api.study.exception;

import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.global.exception.CustomException;
import com.flytrap.venusplanner.global.exception.GeneralExceptionType;
import org.springframework.http.HttpStatus;

public class StudyExceptionType extends GeneralExceptionType  {

    public static CustomException StudyNotFoundException(Long studyId) {
        return DomainNotFoundException(Study.class, studyId);
    }

    public static CustomException StudyMismatchException(String message) {
        return GeneralException(HttpStatus.BAD_REQUEST, message);
    }

    public static CustomException StudyAlreadyJoinedException() {
        return DuplicateDomainException("이미 스터디에 가입되어 있습니다.");
    }

}
