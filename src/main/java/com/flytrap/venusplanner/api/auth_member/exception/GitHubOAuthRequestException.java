package com.flytrap.venusplanner.api.auth_member.exception;

public class GitHubOAuthRequestException extends RuntimeException {
    public GitHubOAuthRequestException(String message) {
        super(message);
    }
}
