package com.flytrap.venusplanner.api.member.domain;

import com.flytrap.venusplanner.global.auth.infrastructure.dto.StandardizedUserResource;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String oauthPk;

    @NotNull
    private Long oauthPlatformId;

    @NotNull
    private String email;

    @NotNull
    private String profileImageUrl;

    @NotNull
    private String nickname;

    @CreatedDate
    private Instant createdTime;

    @Builder
    private Member(String oauthPk, Long oauthPlatformId, String email, String profileImageUrl, String nickname) {
        this.oauthPk = oauthPk;
        this.oauthPlatformId = oauthPlatformId;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }

    public static Member from(StandardizedUserResource userResource) {
        return Member.builder()
                .oauthPk(userResource.oauthPk())
                .oauthPlatformId(userResource.authPlatformType().getId())
                .email(userResource.email())
                .profileImageUrl(userResource.profileUrl())
                .nickname(userResource.nickname())
                .build();
    }
}
