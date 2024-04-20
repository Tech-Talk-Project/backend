package com.example.backend.board.domain;

import com.example.backend.board.dto.request.BoardCreateRequestDto;
import com.example.backend.board.dto.request.BoardUpdateRequestDto;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class ProjectBoard extends BoardEntity {
    @Id @GeneratedValue
    @Column(name = "project_board_id")
    private Long id;

    private String recruitmentPosition;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThumbsUp> thumbsUps = new HashSet<>();

    private boolean recruitmentActive = true;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    public ProjectBoard(Member member, BoardCreateRequestDto dto) {
        this.author = member;
        this.title = dto.getTitle();
        this.recruitmentPosition = dto.getRecruitmentPosition();
        this.content = dto.getContent();
        dto.getTags().forEach(tag -> addTag(new Tag(tag)));
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setProjectBoard(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setProjectBoard(this);
    }

    public void addThumbsUp(ThumbsUp thumbsUp) {
        thumbsUps.add(thumbsUp);
        thumbsUp.setProjectBoard(this);
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        tags.forEach(this::addTag);
    }

    public void update(BoardUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        updateTags(dto.getTags().stream().map(Tag::new).toList());
    }

    public void toggleRecruitment() {
        recruitmentActive = !recruitmentActive;
    }
}
