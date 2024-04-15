package com.example.backend.board.domain;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class ProjectBoard extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "project_board_id")
    private Long id;

    private String title;

    private String recruitPosition;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    private Long viewCount = 0L;

    private Long likeCount = 0L;

    private Long dislikeCount = 0L;

    private boolean recruitmentActive = true;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();
}
