package com.flytrap.venusplanner.global.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.github")
public record GitHubOAuthProperties(
        String clientId,
        String clientSecret,
        String userResourceUri,
        String userEmailResourceUri,
        String accessTokenUri,
        String redirectUri
) {
}
