package com.flytrap.venusplanner.acceptance.join_request.test;

import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.리더;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.리더_멤버_스터디;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.멤버_01;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.멤버_스터디;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.수락된_가입_요청;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.신규_가입_요청;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.알고리즘_스터디;
import static com.flytrap.venusplanner.acceptance.join_request.step.JoinRequestStep.스터디_가입_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.flytrap.venusplanner.acceptance.AcceptanceTest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import com.flytrap.venusplanner.api.join_request.infrastructure.repository.JoinRequestRepository;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member.infrastructure.repository.MemberRepository;
import com.flytrap.venusplanner.api.member_study.infrastructure.repository.MemberStudyRepository;
import com.flytrap.venusplanner.api.study.domain.Study;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[인수테스트] 스터디 가입 요청 케이스")
public class CreateJoinRequestTest extends AcceptanceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    MemberStudyRepository memberStudyRepository;

    @Autowired
    JoinRequestRepository joinRequestRepository;

    @Test
    void 유저는_스터디에_가입_요청을_할_수_있다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var sessionCookie = 테스트_로그인(member.getId());

        // when
        var response = 스터디_가입_요청(study.getId(), sessionCookie);

        // then
        응답_코드는_201_CREATED_를_반환한다(response);
        응답_바디로_생성된_가입_요청의_ID를_반환한다(response);
        스터디_가입_요청이_생성되었는지_검증(response, study, member);
    }

    @Test
    void 존재하지_않는_스터디에_가입_요청을_보낼_수_없다() {
        // given
        var member = memberRepository.save(멤버_01());
        var sessionCookie = 테스트_로그인(member.getId());

        // when
        var response = 스터디_가입_요청(999L, sessionCookie);

        // then
        응답_코드는_404_NOT_FOUND_를_반환한다(response);
    }

    @Test
    void 이미_가입_요청된_상태의_스터디에_가입_요청을_보낼_수_없다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var joinRequest = joinRequestRepository.save(신규_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(member.getId());

        // when
        var response = 스터디_가입_요청(study.getId(), sessionCookie);

        // then
        응답_코드는_409_CONFLICT_를_반환한다(response);
    }

    @Test
    void 이미_가입된_스터디에_가입_요청을_보낼_수_없다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var memberStudy = memberStudyRepository.save(멤버_스터디(study, member));
        var joinRequest = joinRequestRepository.save(수락된_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(member.getId());

        // when
        var response = 스터디_가입_요청(study.getId(), sessionCookie);

        // then
        응답_코드는_409_CONFLICT_를_반환한다(response);
    }

    private void 응답_바디로_생성된_가입_요청의_ID를_반환한다(ExtractableResponse<Response> response) {
        assertThat(response.body().jsonPath().getLong("id"))
                .isNotNull();
    }

    private void 스터디_가입_요청이_생성되었는지_검증(ExtractableResponse<Response> response, Study study, Member member) {
        var createdId = response.body().jsonPath().getLong("id");
        var created = joinRequestRepository.findById(createdId).get();

        assertAll(
                () -> assertThat(created.getStudyId()).isEqualTo(study.getId()),
                () -> assertThat(created.getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(created.getState()).isEqualTo(JoinRequestState.WAIT)
        );
    }

}
