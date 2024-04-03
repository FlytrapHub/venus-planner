package com.flytrap.venusplanner.api.member_study.business.service;

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
public class MemberStudyService {

    private final MemberStudyRepository memberStudyRepository;

    public void saveLeaderStudy(SessionMember leader, Study study) {
        MemberStudy memberStudy = MemberStudy.fromLeader(
                leader.id(),
                study
        );
        memberStudyRepository.save(memberStudy);
    }
}
