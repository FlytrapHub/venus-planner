package com.flytrap.venusplanner.api.join_request.exception;

public class DuplicateJoinRequestException extends RuntimeException {
    public DuplicateJoinRequestException(String message) {
        super(message);
    }
}
