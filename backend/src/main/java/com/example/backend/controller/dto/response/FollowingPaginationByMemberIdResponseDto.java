package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import lombok.Data;

import java.util.List;

@Data
public class FollowingPaginationByMemberIdResponseDto extends ProfilePaginationResponseDto{
    private Long nextCursor;

    public FollowingPaginationByMemberIdResponseDto(List<Member> members, Long nextCursor) {
        if (members.isEmpty()) {
            this.nextCursor = null;
            this.count = 0;
            return;
        }
        for (Member member : members) {
            ProfileData profileData = ProfileData.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .job(member.getProfile().getJob())
                    .introduction(member.getProfile().getIntroduction())
                    .imageUrl(member.getImageUrl())
                    .skills(member.getProfile().getProfileSkills())
                    .build();
            data.add(profileData);
        }
        this.count = members.size();
        this.nextCursor = nextCursor;
    }
}
