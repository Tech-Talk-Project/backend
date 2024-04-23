package com.example.backend.board.domain;

import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ThumbsDown {
    @Id @GeneratedValue
    @Column(name = "thumbs_down_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_board_id")
    private QuestionBoard questionBoard;

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }

    public ThumbsDown(Member member) {
        this.member = member;
    }
}
