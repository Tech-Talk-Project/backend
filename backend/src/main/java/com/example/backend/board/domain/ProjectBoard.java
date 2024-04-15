package com.example.backend.board.domain;

import com.example.backend.board.dto.request.ProjectCreateRequestDto;
import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
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

    public ProjectBoard(Member member, ProjectCreateRequestDto dto) {
        this.author = member;
        this.title = dto.getTitle();
        this.recruitPosition = dto.getRecruitPosition();
        this.content = dto.getContent();
        dto.getTags().forEach(tag -> addTag(new Tag(tag)));
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setProjectBoard(this);
    }
}
