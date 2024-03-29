package com.flytrap.venusplanner.acceptance.common;

import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthMemberController {

    @Value("${auth.session.sessionName}")
    private String sessionName;

    @PostMapping("/api/v1/test/sign-in")
    public void signIn(@RequestBody Long memberId, HttpSession session) {
        session.setAttribute(sessionName, new SessionMember(memberId));
    }
}
