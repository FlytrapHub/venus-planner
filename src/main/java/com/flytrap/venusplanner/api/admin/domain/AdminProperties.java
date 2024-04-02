package com.flytrap.venusplanner.api.admin.domain;

import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record AdminProperties(
        String code,
        Long id
) {

    public boolean isAdminCode(String code) {
        return this.code.equals(code);
    }

    public SessionMember toSessionMember() {
        return new SessionMember(id);
    }
}
