package com.flytrap.venusplanner.api.join_request.business.service;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.exception.JoinRequestAlreadyHandledException;
import com.flytrap.venusplanner.api.join_request.exception.StudyAlreadyJoinedException;
import com.flytrap.venusplanner.api.join_request.exception.StudyMismatchException;
import com.flytrap.venusplanner.api.member_study.business.service.MemberStudyValidator;
import com.flytrap.venusplanner.api.study.business.service.StudyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinRequestCurdFacadeService {

    private final JoinRequestUpdater joinRequestUpdater;
    private final JoinRequestValidator joinRequestValidator;
    private final StudyValidator studyValidator;
    private final MemberStudyValidator memberStudyValidator;

    @Transactional
    public JoinRequest createJoinRequest(Long studyId, Long memberId) {
        studyValidator.validateStudyExists(studyId);

        if (memberStudyValidator.validateMemberBelongsToStudy(memberId, studyId)) {
            throw new StudyAlreadyJoinedException("이미 스터디에 가입되어 있습니다.");
        }

        joinRequestValidator.validateWaitingJoinRequestExists(studyId, memberId);

        return joinRequestUpdater.saveJoinRequest(studyId, memberId);
    }

    @Transactional
    public void acceptJoinRequest(Long studyId, Long requestId, Long memberId) {
        studyValidator.validateStudyExists(studyId);
        memberStudyValidator.validateMemberCanAcceptJoinRequest(memberId, studyId);

        JoinRequest joinRequest = joinRequestValidator.findById(requestId);

        if (!joinRequest.validateStudyIdMatch(studyId)) {
            throw new StudyMismatchException("요청한 스터디 ID와 가입 요청의 스터디 ID가 일치하지 않습니다");
        }

        if (!joinRequest.isWaiting()) {
            throw new JoinRequestAlreadyHandledException("이미 처리된 요청입니다.");
        }

        joinRequest.accept();
    }

    @Transactional
    public void rejectJoinRequest(Long studyId, Long requestId, Long memberId) {
        studyValidator.validateStudyExists(studyId);
        memberStudyValidator.validateMemberCanRejectJoinRequest(memberId, studyId);

        JoinRequest joinRequest = joinRequestValidator.findById(requestId);

        if (!joinRequest.validateStudyIdMatch(studyId)) {
            throw new StudyMismatchException("요청한 스터디 ID와 가입 요청의 스터디 ID가 일치하지 않습니다");
        }

        if (!joinRequest.isWaiting()) {
            throw new JoinRequestAlreadyHandledException("이미 처리된 요청입니다.");
        }

        joinRequest.reject();
    }

}
