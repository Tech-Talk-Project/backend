package com.example.backend.board.domain;

import com.example.backend.board.dto.request.CommentUpdateRequestDto;
import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    public void update(CommentUpdateRequestDto dto) {
        this.content = dto.getContent();
    }

    public Comment(String content, Member author) {
        this.content = content;
        this.author = author;
    }

    public void setProjectBoard(ProjectBoard projectBoard) {
        this.projectBoard = projectBoard;
    }
}
