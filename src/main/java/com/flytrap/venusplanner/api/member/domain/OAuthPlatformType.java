package com.flytrap.venusplanner.api.member.domain;

import static com.flytrap.venusplanner.api.member.exception.MemberExceptionType.NoSuchOauthPlatformTypeException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthPlatformType {
    GITHUB(1L);

    private final Long id;

    public static OAuthPlatformType fromName(String name) {
        return Arrays.stream(OAuthPlatformType.values())
                .filter(type -> Objects.equals(type.name(), name))
                .findFirst()
                .orElseThrow(() -> NoSuchOauthPlatformTypeException(name));
    }

}
