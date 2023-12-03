package com.example.backend.entity.dto;

import com.example.backend.controller.dto.InfoDto;
import com.example.backend.entity.profile.Link;
import com.example.backend.entity.profile.Profile;
import com.example.backend.entity.profile.ProfileSkill;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProfileWithAllDto {
    Profile profile;
    List<Link> links;
    List<ProfileSkill> profileSkills;

    public ProfileWithAllDto(Profile profile, List<Link> links, List<ProfileSkill> profileSkills) {
        this.profile = profile;
        this.links = links;
        this.profileSkills = profileSkills;
    }
}
