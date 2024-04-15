package com.flytrap.venusplanner.api.join_request.business.service;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;

public interface JoinRequestUpdater {

    JoinRequest saveJoinRequest(Long studyId, Long memberId);
}
