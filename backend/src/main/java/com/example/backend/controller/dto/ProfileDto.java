package com.example.backend.controller.dto;

import com.example.backend.entity.profile.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    public ProfileDto(Long memberId, String name, String job, String introduction, String imageUrl, boolean isFollowing, List<Skill> skills) {
        this.memberId = memberId;
        this.name = name;
        this.job = job;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.isFollowing = isFollowing;
        this.skills = new ArrayList<>();
        for (Skill skill : skills) {
            this.skills.add(skill.getName());
        }
    }
}
