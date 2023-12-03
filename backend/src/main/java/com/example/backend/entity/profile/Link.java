package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {
    @Id @GeneratedValue
    @Column(name = "link_id")
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Link(String url, Profile profile) {
        this.url = url;
        this.profile = profile;
    }
}
