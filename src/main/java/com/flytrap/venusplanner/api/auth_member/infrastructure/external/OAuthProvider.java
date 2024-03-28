package com.flytrap.venusplanner.api.auth_member.infrastructure.external;

import com.flytrap.venusplanner.api.auth_member.infrastructure.dto.StandardizedUserResource;

public interface OAuthProvider {

    StandardizedUserResource authenticateAndFetchUserResource(String code);
}
