package com.example.backend.board.domain;

import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Like {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    public void setProjectBoard(ProjectBoard projectBoard) {
        this.projectBoard = projectBoard;
    }

    public Like(Member member) {
        this.member = member;
    }
}
