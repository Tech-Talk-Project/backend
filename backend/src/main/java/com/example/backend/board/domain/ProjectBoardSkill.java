package com.example.backend.board.domain;

import com.example.backend.entity.profile.Skill;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ProjectBoardSkill {
    @Id @GeneratedValue
    @Column(name = "project_board_skill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_board_id")
    private ProjectBoard projectBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;
}
