package com.flytrap.venusplanner.api.join_request.presentation.controller;

import com.flytrap.venusplanner.api.join_request.business.service.JoinRequestCurdFacadeService;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.presentation.dto.response.JoinRequestCreateResponse;
import com.flytrap.venusplanner.global.auth.annotation.SignIn;
import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinRequestController {

    private final JoinRequestCurdFacadeService joinRequestCrudService;

    @PostMapping("/api/v1/studies/{studyId}/join-requests")
    public ResponseEntity<JoinRequestCreateResponse> requestToJoinStudy(
            @PathVariable Long studyId,
            @SignIn SessionMember sessionMember
    ) {
        JoinRequest joinRequest = joinRequestCrudService
                .createJoinRequest(studyId, sessionMember.id());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JoinRequestCreateResponse(joinRequest.getId()));
    }

    @PatchMapping("/api/v1/studies/{studyId}/join-requests/{requestId}/accept")
    public ResponseEntity<Void> acceptJoinRequest(
            @PathVariable Long studyId,
            @PathVariable Long requestId,
            @SignIn SessionMember sessionMember
    ) {
        joinRequestCrudService.acceptJoinRequest(studyId, requestId, sessionMember.id());

        return ResponseEntity.ok(null);
    }

}
