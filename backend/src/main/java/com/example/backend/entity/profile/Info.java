package com.example.backend.entity.profile;

import lombok.*;

@Getter @Setter
public class Info {
    private String nickname;
    private String job;
    private String email;

    @Builder
    public Info(String nickname, String job, String email) {
        this.nickname = nickname;
        this.job = job;
        this.email = email;
    }
}
