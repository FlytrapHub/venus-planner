package com.flytrap.venusplanner.global.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.session")
public record AuthSessionProperties(
        String sessionName
) {
}
