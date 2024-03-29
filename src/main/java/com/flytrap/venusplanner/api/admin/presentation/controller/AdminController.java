package com.flytrap.venusplanner.api.admin.presentation.controller;

import com.flytrap.venusplanner.api.admin.domain.AdminProperties;
import com.flytrap.venusplanner.api.admin.presentation.dto.request.AdminSignInRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminProperties adminProperties;

    @PostMapping("/api/v1/admin/sign-in")
    public ResponseEntity<Void> signIn(
            @RequestBody AdminSignInRequest request,
            HttpSession httpSession
    ) {
        if (adminProperties.isAdminCode(request.code())) {
            httpSession.setAttribute("admin", adminProperties.toSessionMember());

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
