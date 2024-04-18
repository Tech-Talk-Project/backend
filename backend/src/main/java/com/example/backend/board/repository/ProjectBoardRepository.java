package com.example.backend.board.repository;

import com.example.backend.board.domain.ProjectBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.board.domain.QProjectBoard.projectBoard;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectBoardRepository {
    private final JPAQueryFactory query;
    private final EntityManager em;

    @Transactional
    public void save(ProjectBoard projectBoard) {
        em.persist(projectBoard);
    }

    public ProjectBoard findById(Long projectBoardId) {
        return query
                .selectFrom(projectBoard)
                .where(projectBoard.id.eq(projectBoardId))
                .fetchOne();
    }


    public ProjectBoard findByIdWithAll(Long projectBoardId) {
        return query
                .selectFrom(projectBoard)
                .join(projectBoard.author).fetchJoin()
                .where(projectBoard.id.eq(projectBoardId))
                .fetchOne();
    }

    @Transactional
    public void remove(ProjectBoard projectBoard) {
        em.remove(projectBoard);
    }
}
