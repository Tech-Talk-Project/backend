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
@Getter
@NoArgsConstructor
public class QuestionBoard extends BoardEntity {
    @Id
    @GeneratedValue
    @Column(name = "question_board_id")
    private Long id;

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThumbsUp> thumbsUps = new HashSet<>();

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ThumbsDown> thumbsDowns = new HashSet<>();

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    public void addThumbsUp(ThumbsUp thumbsUp) {
        thumbsUps.add(thumbsUp);
        thumbsUp.setQuestionBoard(this);
    }

    public void addThumbsDown(ThumbsDown thumbsDown) {
        thumbsDowns.add(thumbsDown);
        thumbsDown.setQuestionBoard(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setQuestionBoard(this);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.setQuestionBoard(this);
    }

    public QuestionBoard(Member member, BoardCreateRequestDto dto) {
        this.author = member;
        this.title = dto.getTitle();
        this.content = dto.getContent();
        dto.getTags().forEach(tag -> addTag(new Tag(tag)));
    }

    public void update(BoardUpdateRequestDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        updateTags(dto.getTags().stream().map(Tag::new).toList());
    }

    private void updateTags(List<Tag> tags) {
        this.tags.clear();
        tags.forEach(this::addTag);
    }
}
