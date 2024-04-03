package com.flytrap.venusplanner.api.member.infrastructure.repository;

import com.flytrap.venusplanner.api.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthPk(String oauthPk);
}
