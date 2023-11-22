package com.example.backend.entity.member;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Authority {
    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EAuthority eAuthority;
}
