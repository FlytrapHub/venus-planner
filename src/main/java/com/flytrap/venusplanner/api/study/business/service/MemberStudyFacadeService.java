package com.flytrap.venusplanner.api.study.business.service;

import com.flytrap.venusplanner.api.member_study.business.service.MemberStudyService;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.presentation.dto.StudyCreateDto;
import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberStudyFacadeService {

    private final StudyService studyService;
    private final MemberStudyService memberStudyService;

    @Transactional
    public Study saveLeaderStudy(SessionMember leader, StudyCreateDto.Request request) {
        Study study = studyService.saveStudy(request.toEntity());
        memberStudyService.saveLeaderStudy(leader, study);

        return study;
    }
}
