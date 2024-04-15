package com.example.backend.board.domain;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class ProjectBoard extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "project_board_id")
    private Long id;

    private String title;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    private Long viewCount;

    private Long likeCount;

    private Long dislikeCount;

    private boolean isRecruiting;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectBoardSkill> projectBoardSkills = new HashSet<>();

}
