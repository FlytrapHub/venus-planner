package com.flytrap.venusplanner.api.study.business.service;

import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService {

    private final StudyRepository studyRepository;

    public Study findById(Long studyId) {
        //TODO: optional null 처리
        return studyRepository.findById(studyId).get();
    }
}
