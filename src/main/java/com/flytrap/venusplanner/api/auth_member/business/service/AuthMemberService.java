package com.flytrap.venusplanner.api.auth_member.business.service;

import com.flytrap.venusplanner.api.auth_member.infrastructure.external.OAuthProvider;
import com.flytrap.venusplanner.api.auth_member.presentation.dto.SignInDto;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthMemberService {

    private final OAuthProvider oAuthProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public Member authenticateAndFetchMember(SignInDto.Request request) {

        var userResource = oAuthProvider.authenticateAndFetchUserResource(request.code());
        var authenticatedMemberOptional = memberRepository.findByOauthPk(userResource.oauthPk());

        Member authenticatedMember;
        if (authenticatedMemberOptional.isPresent()) {
            authenticatedMember = authenticatedMemberOptional.get();
            authenticatedMember.updateFrom(userResource);
        } else {
            authenticatedMember = memberRepository.save(Member.from(userResource));
        }

        return authenticatedMember;
    }
}
