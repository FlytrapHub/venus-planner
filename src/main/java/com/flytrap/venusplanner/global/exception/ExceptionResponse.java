package com.flytrap.venusplanner.global.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String message;

    private ExceptionResponse(String message) {
        this.message = message;
    }

    public static ExceptionResponse from(Exception exception) {
        return new ExceptionResponse(exception.getMessage());
    }
}
