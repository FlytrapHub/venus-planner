package com.flytrap.venusplanner.api.join_request.infrastructure.repository;

import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {

    @Query("SELECT r "
            + "FROM JoinRequest r "
            + "WHERE r.memberId = :memberId "
            + "AND r.studyId = :studyId "
            + "ORDER BY r.createdTime DESC")
    Page<JoinRequest> findMostRecentByMemberIdAndStudyId(
            @Param("memberId") Long memberId,
            @Param("studyId") Long studyId,
            Pageable pageable);
}
