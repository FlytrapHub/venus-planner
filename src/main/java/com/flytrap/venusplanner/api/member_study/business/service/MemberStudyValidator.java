package com.flytrap.venusplanner.api.member_study.business.service;

public interface MemberStudyValidator {

    boolean validateMemberBelongsToStudy(Long memberId, Long studyId);
    void validateMemberCanAcceptJoinRequest(Long memberId, Long studyId);
    void validateMemberCanRejectJoinRequest(Long memberId, Long studyId);
}
