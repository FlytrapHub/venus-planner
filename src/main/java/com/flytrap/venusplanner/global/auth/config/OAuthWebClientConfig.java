package com.flytrap.venusplanner.global.auth.config;

import com.flytrap.venusplanner.global.auth.properties.GitHubOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class OAuthWebClientConfig {

    private final GitHubOAuthProperties gitHubOAuthProperties;

    @Component
    public class GitHubOAuthFormDataBuilder {
        public MultiValueMap<String, String> buildFormData(String code) {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("code", code);
            formData.add("client_id", gitHubOAuthProperties.clientId());
            formData.add("client_secret", gitHubOAuthProperties.clientSecret());

            return formData;
        }
    }

    @Bean(name = "gitHubAccessTokenClient")
    public WebClient gitHubAccessTokenClient() {
        return WebClient.builder().baseUrl(gitHubOAuthProperties.accessTokenUri()).build();
    }

    @Bean(name = "gitHubUserResourceClient")
    public WebClient gitHubUserResourceClient() {
        return WebClient.builder().baseUrl(gitHubOAuthProperties.userResourceUri()).build();
    }

    @Bean(name = "gitHubEmailResourceClient")
    public WebClient gitHubEmailResourceClient() {
        return WebClient.builder().baseUrl(gitHubOAuthProperties.userEmailResourceUri()).build();
    }
}
