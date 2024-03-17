package com.flytrap.venusplanner.api.join_request.exception;

public class JoinRequestAlreadyHandledException extends RuntimeException {
    public JoinRequestAlreadyHandledException(String message) {
        super(message);
    }
}
