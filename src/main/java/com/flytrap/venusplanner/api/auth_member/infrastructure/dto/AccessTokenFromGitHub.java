package com.flytrap.venusplanner.api.auth_member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenFromGitHub(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType
) {
    public String getHeadValue() {
        return String.format("%s %s", tokenType, accessToken);
    }
}