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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_board_id")
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_board_id")
    private QuestionBoard questionBoard;

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }

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

    public void setStudyBoard(StudyBoard studyBoard) {
        this.studyBoard = studyBoard;
    }
}
