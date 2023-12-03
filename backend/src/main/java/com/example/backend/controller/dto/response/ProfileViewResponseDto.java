package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.InfoDto;
import com.example.backend.entity.dto.ProfileWithAllDto;
import com.example.backend.entity.profile.Link;
import com.example.backend.entity.profile.Profile;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProfileViewResponseDto {

    private InfoDto info;

    private String imageUrl;

    private String introduction;

    private List<String> links;

    private Set<String> skills;

    private String detailedDescription;

    public ProfileViewResponseDto(ProfileWithAllDto profileWithAllDto) {
        Profile profile = profileWithAllDto.getProfile();
        this.info = new InfoDto(profile.getInfo());
        this.imageUrl = profile.getImageUrl();
        this.introduction = profile.getIntroduction();
        this.links = profileWithAllDto.getLinks().stream()
                .map(Link::getUrl)
                .collect(Collectors.toList());
        this.skills = profileWithAllDto.getProfileSkills().stream()
                .map(profileSkill -> profileSkill.getSkill().getName())
                .collect(Collectors.toSet());
        this.detailedDescription = profile.getDetailedDescription();
    }
}
