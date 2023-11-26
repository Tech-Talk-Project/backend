package com.example.backend;

import com.example.backend.entity.member.Authority;
import com.example.backend.entity.member.EAuthority;
import com.example.backend.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitApp {
    private final InitAuthority initAuthority;

    @PostConstruct
    public void init() {
        initAuthority.init();
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
}
