package com.example.backend.board.repository;

import com.example.backend.board.domain.ProjectBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardRepository {
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Transactional
    public void save(ProjectBoard projectBoard) {
        em.persist(projectBoard);
        em.flush();
    }
}
