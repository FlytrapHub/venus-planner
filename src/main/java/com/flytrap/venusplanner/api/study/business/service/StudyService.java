package com.flytrap.venusplanner.api.study.business.service;

import com.flytrap.venusplanner.api.join_request.exception.StudyNotFoundException;
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
        //TODO: optional null 처리
        return studyRepository.findById(studyId).get();
    }

    @Override
    public void validateStudyExists(Long studyId) {
        if (!studyRepository.existsById(studyId)) {
            // TODO: 예외처리 방식 결정되면 변경하기
            throw new StudyNotFoundException("스터디를 찾을 수 없습니다.");
        }
    }
}
