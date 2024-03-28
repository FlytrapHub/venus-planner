package com.flytrap.venusplanner.api.join_request.business.service;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;

public interface JoinRequestValidator {
    void validateWaitingJoinRequestExists(Long studyId, Long memberId);
    JoinRequest findById(Long joinRequestId);
}
