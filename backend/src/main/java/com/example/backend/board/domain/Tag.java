package com.example.backend.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Tag {
    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_board_id")
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_board_id")
    private QuestionBoard questionBoard;

    public Tag(String name) {
        this.name = name;
    }

    public void setProjectBoard(ProjectBoard projectBoard) {
        this.projectBoard = projectBoard;
    }

    public void setStudyBoard(StudyBoard studyBoard) {
        this.studyBoard = studyBoard;
    }

    public void setQuestionBoard(QuestionBoard questionBoard) {
        this.questionBoard = questionBoard;
    }
}
