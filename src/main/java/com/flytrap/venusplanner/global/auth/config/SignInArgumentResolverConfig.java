package com.flytrap.venusplanner.global.auth.config;

import com.flytrap.venusplanner.global.auth.resolver.SignInArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SignInArgumentResolverConfig implements WebMvcConfigurer {

    private final SignInArgumentResolver signInArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(signInArgumentResolver);
    }

}
