package com.example.backend;

import com.example.backend.entity.member.Authority;
import com.example.backend.entity.member.EAuthority;
import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Skill;
import com.example.backend.repository.member.AuthorityRepository;
import com.example.backend.repository.profile.SkillRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitApp {
    private final InitAuthority initAuthority;
    private final InitSkill initSkill;

    @PostConstruct
    public void init() {
        initAuthority.init();
        initSkill.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitAuthority {
        private final AuthorityRepository authorityRepository;

        public void init() {
            EAuthority[] authorities = EAuthority.values();
            for (EAuthority authority : authorities) {
                authorityRepository.save(new Authority(authority));
            }
        }
    }

    @Component
    @RequiredArgsConstructor
    static class InitSkill {
        private final SkillRepository skillRepository;

        public void init() {
            ESkill[] skills = ESkill.values();
            for (ESkill skill : skills) {
                skillRepository.save(new Skill(skill));
            }
        }
    }
}
