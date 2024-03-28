package com.flytrap.venusplanner.api.member_study.domain;

import com.flytrap.venusplanner.api.access.domain.Permission;
import com.flytrap.venusplanner.api.access.domain.Roll;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.global.entity.TimeAuditingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStudy extends TimeAuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    @ManyToOne
    private Study study;

    private Long rollId;
    private Long permissionId;

    @Builder
    private MemberStudy(Long memberId, Study study, Long rollId, Long permissionId) {
        this.memberId = memberId;
        this.study = study;
        this.rollId = rollId;
        this.permissionId = permissionId;
    }

    public static MemberStudy fromLeader(Long leaderId, Study study) {
        return MemberStudy.builder()
                .memberId(leaderId)
                .study(study)
                .rollId(Roll.LEADER.getId())
                .permissionId(Permission.EDIT.getId())
                .build();
    }

    public boolean isLeader() {
        return Objects.equals(rollId, Roll.LEADER.getId());
    }

    public boolean canAcceptStudyJoinRequest() {
        return isLeader();
    }

    public boolean canRejectStudyJoinRequest() {
        return isLeader();
    }
}
