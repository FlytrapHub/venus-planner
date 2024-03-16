package com.flytrap.venusplanner.api.join_request.exception;

public class StudyAlreadyJoinedException extends RuntimeException {
    public StudyAlreadyJoinedException(String message) {
        super(message);
    }
}
