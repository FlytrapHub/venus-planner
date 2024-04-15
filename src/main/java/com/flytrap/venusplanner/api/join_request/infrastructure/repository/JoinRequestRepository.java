package com.flytrap.venusplanner.api.join_request.infrastructure.repository;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    boolean existsByMemberIdAndStudyIdAndState(Long memberId, Long studyId, JoinRequestState state);
}
