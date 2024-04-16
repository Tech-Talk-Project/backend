package com.example.backend.board.domain;

import com.example.backend.board.dto.request.ProjectCreateRequestDto;
import com.example.backend.board.dto.request.ProjectUpdateRequestDto;
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
public class ProjectBoard extends BoardEntity {
    @Id @GeneratedValue
    @Column(name = "project_board_id")
    private Long id;

    private String recruitPosition;

    @OneToMany(mappedBy = "projectBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

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

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setProjectBoard(this);
    }

    public void updateTags(List<Tag> tags) {
        this.tags.clear();
        tags.forEach(this::addTag);
    }

    public void update(ProjectUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        updateTags(dto.getTags().stream().map(Tag::new).toList());
    }


    public void toggleRecruitment() {
        recruitmentActive = !recruitmentActive;
    }
}
