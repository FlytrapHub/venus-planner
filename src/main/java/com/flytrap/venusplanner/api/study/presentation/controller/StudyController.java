package com.flytrap.venusplanner.api.study.presentation.controller;

import com.flytrap.venusplanner.api.study.business.service.MemberStudyFacadeService;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.presentation.dto.StudyCreateDto;
import com.flytrap.venusplanner.global.auth.annotation.SignIn;
import com.flytrap.venusplanner.global.auth.dto.SessionMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyController {

    private final MemberStudyFacadeService memberStudyFacadeService;

    @PostMapping("/api/v1/studies")
    public ResponseEntity<StudyCreateDto.Response> createStudy(
            @SignIn SessionMember leader,
            @Valid @RequestBody StudyCreateDto.Request request
    ) {
        Study study = memberStudyFacadeService.saveLeaderStudy(leader, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StudyCreateDto.Response.from(study));
    }
}
