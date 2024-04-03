package com.flytrap.venusplanner.api.auth_member.presentation.controller;

import com.flytrap.venusplanner.api.auth_member.business.service.AuthMemberService;
import com.flytrap.venusplanner.api.auth_member.presentation.dto.SignInDto;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import com.flytrap.venusplanner.global.auth.properties.AuthSessionProperties;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthMemberController {

    private final AuthSessionProperties authSessionProperties;
    private final AuthMemberService authMemberService;

    @PostMapping("/api/v1/auth/sign-in")
    public ResponseEntity<SignInDto.Response> signIn(
            @RequestBody SignInDto.Request request,
            HttpSession session
    ) {
        Member member = authMemberService.authenticateAndFetchMember(request);
        session.setAttribute(authSessionProperties.sessionName(), SessionMember.from(member));

        return ResponseEntity.ok().body(SignInDto.Response.from(member));
    }

    @DeleteMapping("/api/v1/auth/sign-out")
    public ResponseEntity<Void> signOut(HttpSession session) {

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
