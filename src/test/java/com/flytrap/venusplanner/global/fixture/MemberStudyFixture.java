package com.flytrap.venusplanner.global.fixture;

import com.flytrap.venusplanner.api.access.domain.Permission;
import com.flytrap.venusplanner.api.access.domain.Roll;
import com.flytrap.venusplanner.api.member.domain.Member;
import com.flytrap.venusplanner.api.member_study.domain.MemberStudy;
import com.flytrap.venusplanner.api.study.domain.Study;

public class MemberStudyFixture {
    public static MemberStudy 리더_멤버_스터디(Study study, Member leader) {
        return MemberStudy.fromLeader(leader.getId(), study);
    }

    public static MemberStudy 멤버_스터디(Study study, Member member) {
        return MemberStudy.builder()
                .memberId(member.getId())
                .study(study)
                .rollId(Roll.MEMBER.getId())
                .permissionId(Permission.NONE.getId())
                .build();
    }
}
