package com.flytrap.venusplanner.api.study.business.service;

import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService implements StudyValid {

    private final StudyRepository studyRepository;

    @Transactional
    public Study saveStudy(Study study) {
        return studyRepository.save(study);
    }

    @Override
    public Study findById(Long studyId) {
        //TODO: optional null 처리
        return studyRepository.findById(studyId).get();
    }
}
