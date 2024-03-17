package com.flytrap.venusplanner.api.join_request.business.service;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.exception.DuplicateJoinRequestException;
import com.flytrap.venusplanner.api.join_request.exception.ForbiddenException;
import com.flytrap.venusplanner.api.join_request.exception.JoinRequestAlreadyHandledException;
import com.flytrap.venusplanner.api.join_request.exception.JoinRequestNotFoundException;
import com.flytrap.venusplanner.api.join_request.exception.StudyAlreadyJoinedException;
import com.flytrap.venusplanner.api.join_request.exception.StudyMismatchException;
import com.flytrap.venusplanner.api.join_request.exception.StudyNotFoundException;
import com.flytrap.venusplanner.api.join_request.infrastructure.repository.JoinRequestRepository;
import com.flytrap.venusplanner.api.member_study.domain.MemberStudy;
import com.flytrap.venusplanner.api.member_study.infrastructure.repository.MemberStudyRepository;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JoinRequestCurdFacadeService {

    private final JoinRequestRepository joinRequestRepository;
    private final StudyRepository studyRepository;
    private final MemberStudyRepository memberStudyRepository;

    @Transactional
    public JoinRequest createJoinRequest(Long studyId, Long memberId) {
        if (!studyRepository.existsById(studyId)) {
            throw new StudyNotFoundException("스터디를 찾을 수 없습니다.");
        }

        if (memberStudyRepository.existsByStudyIdAndMemberId(studyId, memberId)) {
            throw new StudyAlreadyJoinedException("이미 스터디에 가입되어 있습니다.");
        }

        joinRequestRepository.findMostRecentByMemberIdAndStudyId(
                        studyId, memberId, PageRequest.of(0, 1))
                .stream().findFirst()
                .ifPresent(joinRequest -> {
                            if (joinRequest.isWaiting()) {
                                throw new DuplicateJoinRequestException("이미 가입 요청된 상태입니다.");
                            }
                        }
                );

        return joinRequestRepository.save(JoinRequest.create(studyId, memberId));
    }

    @Transactional
    public void acceptJoinRequest(Long studyId, Long requestId, Long memberId) {
        if (!studyRepository.existsById(studyId)) {
            throw new StudyNotFoundException("스터디를 찾을 수 없습니다.");
        }

        MemberStudy memberStudy = memberStudyRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> new ForbiddenException("스터디 멤버가 아닙니다."));

        if (!memberStudy.isLeader()) {
            throw new ForbiddenException("수락 권한이 없습니다.");
        }

        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                        .orElseThrow(() -> new JoinRequestNotFoundException("JoinRequest를 찾을 수 없습니다."));

        if (!joinRequest.validateStudyIdMatch(studyId)) {
            throw new StudyMismatchException("요청한 스터디 ID와 가입 요청의 스터디 ID가 일치하지 않습니다");
        }

        if (!joinRequest.isWaiting()) {
            throw new JoinRequestAlreadyHandledException("이미 처리된 요청입니다.");
        }
        joinRequest.accept();
    }

}
