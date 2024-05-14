package com.example.backend;

import com.example.backend.controller.dto.request.UpdateSkillsRequestDto;
import com.example.backend.entity.member.Authority;
import com.example.backend.entity.member.EAuthority;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Skill;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.repository.member.AuthorityRepository;
import com.example.backend.repository.profile.SkillRepository;
import com.example.backend.service.member.MemberCreateService;
import com.example.backend.service.profile.ProfileUpdateService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class InitApp {
    private final InitMember initMember;

    @PostConstruct
    public void init() {
//        initMember.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMember {
        private final MemberCreateService memberCreateService;
        private final ProfileUpdateService profileUpdateService;

        public void init() {
            for (int i = 0; i < 100; i++) {
                UserProfileDto userProfileDto = new UserProfileDto("test" + i, "test"+i+"@com", "test.com");
                Member user = memberCreateService.createUser(userProfileDto, OAuth2Provider.GITHUB);

                int skillCount = getSkillCount();
                List<String> skills = getRandomSkills(skillCount).stream().map(ESkill::getName).toList();

                UpdateSkillsRequestDto updateSkillsRequestDto = new UpdateSkillsRequestDto();
                updateSkillsRequestDto.setSkills(skills);

                profileUpdateService.updateSkills(user.getId(), updateSkillsRequestDto);
            }
        }

        public List<ESkill> getRandomSkills(int n) {
            List<ESkill> shuffledSkills = Arrays.asList(ESkill.values());
            Collections.shuffle(shuffledSkills);
            return shuffledSkills.subList(0, n);
        }

        public int getSkillCount() {
            Random random = new Random();
            return random.nextInt(6);
        }
    }
}
