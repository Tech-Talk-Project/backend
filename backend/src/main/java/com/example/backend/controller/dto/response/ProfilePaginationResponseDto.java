package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.ProfileSkill;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public abstract class ProfilePaginationResponseDto {
    List<ProfileData> data = new ArrayList<>();
    int count;
    @Getter
    @Setter
    protected static class ProfileData {
        private Long memberId;
        private String name;
        private String job;
        private String introduction;
        private String imageUrl;
        private List<String> skills;

        @Builder
        public ProfileData(Long memberId, String name, String job, String introduction, String imageUrl, Set<ProfileSkill> skills) {
            this.memberId = memberId;
            this.name = name;
            this.job = job;
            this.introduction = introduction;
            this.imageUrl = imageUrl;

            this.skills = new ArrayList<>();
            for (ProfileSkill profileSkill : skills) {
                this.skills.add(profileSkill.getSkill().getName());
            }
        }
    }
}
