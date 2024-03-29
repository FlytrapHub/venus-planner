package com.flytrap.venusplanner.acceptance.join_request.test;

import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.거절된_가입_요청;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.리더;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.리더_멤버_스터디;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.멤버_01;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.수락된_가입_요청;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.신규_가입_요청;
import static com.flytrap.venusplanner.acceptance.join_request.fixture.JoinRequestFixture.알고리즘_스터디;
import static com.flytrap.venusplanner.acceptance.join_request.step.JoinRequestStep.스터디_가입_요청_거절;
import static org.assertj.core.api.Assertions.assertThat;

import com.flytrap.venusplanner.acceptance.AcceptanceTest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequest;
import com.flytrap.venusplanner.api.join_request.domain.JoinRequestState;
import com.flytrap.venusplanner.api.join_request.infrastructure.repository.JoinRequestRepository;
import com.flytrap.venusplanner.api.member.infrastructure.repository.MemberRepository;
import com.flytrap.venusplanner.api.member_study.infrastructure.repository.MemberStudyRepository;
import com.flytrap.venusplanner.api.study.infrastructure.repository.StudyRepository;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("[인수테스트] 스터디 가입 요청 거절 케이스")
public class RejectJoinRequestTest extends AcceptanceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    StudyRepository studyRepository;

    @Autowired
    MemberStudyRepository memberStudyRepository;

    @Autowired
    JoinRequestRepository joinRequestRepository;

    @Test
    void 스터디_리더는_스터디_가입_요청을_거절_할_수_있다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var joinRequest = joinRequestRepository.save(신규_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(leader.getId());

        // when
        var response = 스터디_가입_요청_거절(
                study.getId(), joinRequest.getId(), sessionCookie);

        // then
        응답_코드는_200_OK_를_반환한다(response);
        응답_바디로_빈_응답을_반환한다(response);
        스터디_가입_요청이_거절_되었는지_검증(joinRequest);
    }

    @Test
    void 스터디_리더가_아닌_멤버는_스터디_가입_요청을_거절_할_수_없다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var joinRequest = joinRequestRepository.save(신규_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(member.getId());

        // when
        var response = 스터디_가입_요청_거절(
                study.getId(), joinRequest.getId(), sessionCookie);

        // then
        응답_코드는_403_FORBIDDEN_를_반환한다(response);
    }

    @Test
    void 이미_수락된_스터디_가입_요청은_거절_할_수_없다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var joinRequest = joinRequestRepository.save(수락된_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(leader.getId());

        // when
        var response = 스터디_가입_요청_거절(
                study.getId(), joinRequest.getId(), sessionCookie);

        // then
        응답_코드는_409_CONFLICT_를_반환한다(response);
    }

    @Test
    void 이미_거절된_스터디_가입_요청은_거절_할_수_없다() {
        // given
        var leader = memberRepository.save(리더());
        var member = memberRepository.save(멤버_01());
        var study = studyRepository.save(알고리즘_스터디());
        var leaderMemberStudy = memberStudyRepository.save(리더_멤버_스터디(study, leader));
        var joinRequest = joinRequestRepository.save(거절된_가입_요청(study, member));
        var sessionCookie = 테스트_로그인(leader.getId());

        // when
        var response = 스터디_가입_요청_거절(
                study.getId(), joinRequest.getId(), sessionCookie);

        // then
        응답_코드는_409_CONFLICT_를_반환한다(response);
    }

    private void 응답_바디로_빈_응답을_반환한다(ExtractableResponse<Response> response) {
        assertThat(response.body().asString()).isEmpty();
    }

    private void 스터디_가입_요청이_거절_되었는지_검증(JoinRequest joinRequest) {
        var created = joinRequestRepository.findById(joinRequest.getId()).get();

        assertThat(created.getState()).isEqualTo(JoinRequestState.REJECT);
    }

}
