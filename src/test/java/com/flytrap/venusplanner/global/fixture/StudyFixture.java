package com.flytrap.venusplanner.global.fixture;

import com.flytrap.venusplanner.api.study.domain.Study;

public class StudyFixture {

    public static Study 알고리즘_스터디() {
        return new Study("알고리즘 챌린지", "알고리즘 실력을 쌓아갑니다.");
    }

    public static Study 파이썬_스터디() {
        return new Study("Python 스터디", "최신 Python 트렌드를 공부하며 프로젝트를 함께 진행합니다.");
    }
}
