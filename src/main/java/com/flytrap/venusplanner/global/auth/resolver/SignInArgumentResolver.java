package com.flytrap.venusplanner.global.auth.resolver;

import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import com.flytrap.venusplanner.global.auth.annotation.SignIn;
import com.flytrap.venusplanner.global.auth.exception.SessionMemberAuthException;
import com.flytrap.venusplanner.global.auth.properties.AuthSessionProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class SignInArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthSessionProperties authSessionProperties;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean hasSignInAnnotation = parameter.hasParameterAnnotation(SignIn.class);
        boolean hasSessionMemberType = SessionMember.class.isAssignableFrom(parameter.getParameterType());

        return hasSignInAnnotation && hasSessionMemberType;
    }

    @Override
    public SessionMember resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        return extractSessionMember(request)
                .orElseThrow(SessionMemberAuthException::new);
    }

    private Optional<SessionMember> extractSessionMember(HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);

        return Optional.ofNullable(httpSession)
                .map(session -> session.getAttribute(authSessionProperties.sessionName()))
                .filter(SessionMember.class::isInstance)
                .map(SessionMember.class::cast);
    }
}
