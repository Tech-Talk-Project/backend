package com.example.backend.entity.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {
    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    private String name;

    public Authority(EAuthority eAuthority) {
        this.name = eAuthority.name();
    }
}
