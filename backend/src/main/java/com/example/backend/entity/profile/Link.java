package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Link {
    @Id @GeneratedValue
    @Column(name = "link_id")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
