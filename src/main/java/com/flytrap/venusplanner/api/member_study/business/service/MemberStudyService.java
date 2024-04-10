package com.flytrap.venusplanner.api.member_study.business.service;

import static com.flytrap.venusplanner.global.exception.GeneralExceptionType.ForbiddenException;

import com.flytrap.venusplanner.api.member_study.domain.MemberStudy;
import com.flytrap.venusplanner.api.member_study.infrastructure.repository.MemberStudyRepository;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberStudyService implements MemberStudyValidator {

    private final MemberStudyRepository memberStudyRepository;

    public void saveLeaderStudy(SessionMember leader, Study study) {
        MemberStudy memberStudy = MemberStudy.fromLeader(
                leader.id(),
                study
        );
        memberStudyRepository.save(memberStudy);
    }

    @Override
    public boolean validateMemberBelongsToStudy(Long memberId, Long studyId) {
        return memberStudyRepository.existsByStudyIdAndMemberId(studyId, memberId);
    }

    @Override
    public void validateMemberCanAcceptJoinRequest(Long memberId, Long studyId) {
        MemberStudy memberStudy = memberStudyRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> ForbiddenException("스터디 멤버가 아닙니다."));

        if (!memberStudy.canAcceptStudyJoinRequest()) {
            throw ForbiddenException("수락 권한이 없습니다.");
        }
    }

    @Override
    public void validateMemberCanRejectJoinRequest(Long memberId, Long studyId) {
        MemberStudy memberStudy = memberStudyRepository.findByStudyIdAndMemberId(studyId, memberId)
                .orElseThrow(() -> ForbiddenException("스터디 멤버가 아닙니다."));

        if (!memberStudy.canAcceptStudyJoinRequest()) {
            throw ForbiddenException("거절 권한이 없습니다.");
        }
    }
}
