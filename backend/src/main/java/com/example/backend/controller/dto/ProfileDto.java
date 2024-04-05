package com.example.backend.controller.dto;

import com.example.backend.entity.profile.ProfileSkill;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class ProfileDto {
    private Long memberId;
    private String name;
    private String job;
    private String introduction;
    private String imageUrl;
    private boolean isFollowing;
    private List<String> skills;

    @Builder
    public ProfileDto(Long memberId, String name, String job, String introduction, String imageUrl, boolean isFollowing, Set<ProfileSkill> skills) {
        this.memberId = memberId;
        this.name = name;
        this.job = job;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.isFollowing = isFollowing;
        this.skills = new ArrayList<>();
        for (ProfileSkill profileSkill : skills) {
            this.skills.add(profileSkill.getSkill().getName());
        }
    }
}
