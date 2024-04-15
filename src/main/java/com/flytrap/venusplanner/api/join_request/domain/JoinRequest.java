package com.flytrap.venusplanner.api.join_request.domain;

import com.flytrap.venusplanner.global.entity.TimeAuditingBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest extends TimeAuditingBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long studyId;

    @Enumerated(EnumType.STRING)
    private JoinRequestState state;

    @Builder
    private JoinRequest(Long memberId, Long studyId, JoinRequestState state) {
        this.memberId = memberId;
        this.studyId = studyId;
        this.state = state;
    }

    public static JoinRequest create(Long studyId, Long memberId) {
        return JoinRequest.builder()
                .studyId(studyId).memberId(memberId)
                .state(JoinRequestState.WAIT)
                .build();
    }

    public boolean isWaiting() {
        return this.state == JoinRequestState.WAIT;
    }

    public boolean validateStudyIdMatch(Long studyId) {
        return Objects.equals(this.studyId, studyId);
    }

    public void accept() {
        this.state = JoinRequestState.ACCEPT;
    }

    public void reject() {
        this.state = JoinRequestState.REJECT;
    }

}
