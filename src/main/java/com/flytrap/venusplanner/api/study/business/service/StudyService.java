package com.flytrap.venusplanner.api.study.business.service;

import static com.flytrap.venusplanner.api.study.exception.StudyExceptionType.StudyNotFoundException;

import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyService implements StudyValidator {

    private final StudyRepository studyRepository;

    @Transactional
    public Study saveStudy(Study study) {
        return studyRepository.save(study);
    }

    public Study findById(Long studyId) {
        return studyRepository.findById(studyId).orElseThrow(() -> StudyNotFoundException(studyId));
    }

    @Override
    public void validateStudyExists(Long studyId) {
        if (!studyRepository.existsById(studyId)) {
            throw StudyNotFoundException(studyId);
        }
    }
}
