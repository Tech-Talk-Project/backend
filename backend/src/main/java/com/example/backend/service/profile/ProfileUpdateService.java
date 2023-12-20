package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.*;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Profile;
import com.example.backend.entity.profile.Skill;
import com.example.backend.repository.profile.MemberProfileRepository;
import com.example.backend.repository.profile.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileUpdateService {
    private final MemberProfileRepository memberProfileRepository;
    private final SkillRepository skillRepository;

    public void updateInfo(Long memberId, UpdateInfoRequestDto updateInfoRequestDto) {
        String nickname = updateInfoRequestDto.getName();
        String job =  updateInfoRequestDto.getJob();
        memberProfileRepository.updateProfileInfo(memberId, nickname, job);
    }

    public void updateIntroduction(Long memberId, UpdateIntroductionRequestDto updateIntroductionRequestDto) {
        String introduction = updateIntroductionRequestDto.getIntroduction();
        memberProfileRepository.updateProfileIntroduction(memberId, introduction);
    }

    public void updateDescription(Long memberId, UpdateDescRequestDto updateDescRequestDto) {
        String detailedDescription = updateDescRequestDto.getDetailedDescription();
        memberProfileRepository.updateProfileDescription(memberId, detailedDescription);
    }

    public void updateLinks(Long memberId, UpdateLinksRequestDto updateLinksRequestDto) {
        Profile profileWithLinks = memberProfileRepository.findProfileWithLinks(memberId);
        profileWithLinks.updateLinks(updateLinksRequestDto.getLinks());
    }

    public void updateSkills(Long memberId, UpdateSkillsRequestDto updateSkillsRequestDto) {
        Profile profileWithSkills = memberProfileRepository.findProfileWithSkills(memberId);
        List<ESkill> eSkills =
                updateSkillsRequestDto.getSkills().stream().map(ESkill::from).toList();
        List<Skill> skills = eSkills.stream().map(skillRepository::findByESkill).toList();
        profileWithSkills.updateProfileSkills(skills);
    }
}
