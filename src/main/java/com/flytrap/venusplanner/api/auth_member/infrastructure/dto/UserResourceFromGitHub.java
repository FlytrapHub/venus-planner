package com.flytrap.venusplanner.api.auth_member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResourceFromGitHub {

    private Long id; // OAuthPk
    private String email;
    private String login;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    public String getOAuthPk() {
        return String.valueOf(id);
    }

    public boolean isEmailEmpty() {
        return email == null || email.isEmpty();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

}
