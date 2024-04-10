package com.flytrap.venusplanner.api.join_request.business.service;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import com.flytrap.venusplanner.api.join_request.exception.JoinRequestNotFoundException;
import com.flytrap.venusplanner.api.join_request.infrastructure.repository.JoinRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinRequestService implements JoinRequestUpdater, JoinRequestValidator {

    private final JoinRequestRepository joinRequestRepository;

    @Override
    @Transactional
    public JoinRequest saveJoinRequest(Long studyId, Long memberId) {
        return joinRequestRepository.save(JoinRequest.create(studyId, memberId));
    }

    @Override
    public boolean validateWaitingJoinRequestExists(Long studyId, Long memberId) {
        return joinRequestRepository.existsByMemberIdAndStudyIdAndState(
                memberId, studyId, JoinRequestState.WAIT);
    }

    @Override
    public JoinRequest findById(Long joinRequestId) {
        return joinRequestRepository.findById(joinRequestId)
                .orElseThrow(() -> new JoinRequestNotFoundException("JoinRequest를 찾을 수 없습니다."));
        // TODO: 예외처리 방식 결정되면 변경하기
    }
}
