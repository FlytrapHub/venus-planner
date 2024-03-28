package com.flytrap.venusplanner.api.auth_member.infrastructure.dto;

public record UserEmailResourceFromGitHub(
        String email,
        boolean verified,
        boolean primary,
        String visibility
) {
}
