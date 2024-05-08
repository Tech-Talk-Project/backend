package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.ProfileDto;
import com.example.backend.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProfileCursorPaginationResponseDto  {
    List<ProfileDto> data = new ArrayList<>();
    private String nextCursor;

    public ProfileCursorPaginationResponseDto(List<Member> members) {
        if (!members.isEmpty()) {
            this.nextCursor = members.get(members.size() - 1).getUpdatedAt().toString();
        }

        for (Member member : members) {
            ProfileDto profileData = ProfileDto.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .job(member.getProfile().getJob())
                    .introduction(member.getProfile().getIntroduction())
                    .imageUrl(member.getImageUrl())
                    .skills(member.getProfile().getSkills())
                    .build();
            data.add(profileData);
        }
    }
}
