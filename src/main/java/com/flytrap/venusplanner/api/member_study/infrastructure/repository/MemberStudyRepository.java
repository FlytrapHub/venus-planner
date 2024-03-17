package com.flytrap.venusplanner.api.member_study.infrastructure.repository;

import com.flytrap.venusplanner.api.member_study.domain.MemberStudy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {

    boolean existsByStudyIdAndMemberId(Long studyId, Long memberId);
    Optional<MemberStudy> findByStudyIdAndMemberId(Long studyId, Long memberId);
}
