package com.example.backend.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAuthority eAuthority;

    public String getName() {
        return eAuthority.name();
    }

    public Authority(EAuthority eAuthority) {
        this.eAuthority = eAuthority;
    }
}
