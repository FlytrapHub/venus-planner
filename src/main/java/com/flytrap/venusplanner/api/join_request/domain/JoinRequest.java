package com.flytrap.venusplanner.api.join_request.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long studyId;

    @CreatedDate
    private Instant requestTime;

    @Enumerated(EnumType.STRING)
    private JoinRequestState state;

    @Builder
    private JoinRequest(Long memberId, Long studyId, JoinRequestState state) {
        this.memberId = memberId;
        this.studyId = studyId;
        this.state = state;
    }

    public static JoinRequest create(Long studyId, Long memberId) {
        return null;
    }

    public void accept() {

    }

    public void reject() {

    }

}
